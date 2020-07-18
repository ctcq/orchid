package org.ctcq.orchid.view.audio;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

import org.ctcq.orchid.view.audio.exceptions.AudioPlayblackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioPlayer extends Thread {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Singleton instance
     */
    private static AudioPlayer instance;

    private Deque<AudioInputStream> audioQueue;
    private AudioInputStream lastPlayed;

    /**
     * @return Singleton instance.
     */
    public static AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    private AudioPlayer() {
        audioQueue = new ArrayDeque<>();
    }

    /**
     * Play the audio queue.
     * @throws AudioPlayblackException
     */
    public void playQueue() throws AudioPlayblackException {
        while (!isInterrupted() && !audioQueue.isEmpty()) {
            // Fetch info from audiostream
            final AudioInputStream ais = audioQueue.remove();
            lastPlayed = ais;
            logger.info(String.format("Playing audiostream %s...", ais));

            final AudioFormat format = ais.getFormat();
            final DataLine.Info info = new DataLine.Info(Clip.class, format);

            try {
                // Obtain and play the clip
                Clip audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.open();
                audioClip.start();
                audioClip.close();
            } catch (LineUnavailableException e) {
                logger.error(String.format("Could not play audiostream %s.", ais));
                throw new AudioPlayblackException();
            }

            logger.info(String.format("Finished playing audiostream %s", ais));
        }
    }

    /**
     * Pause the queue until resumption.
     */
    public void pauseQueue() {
        interrupt();
    }

    /**
     * Resume the audio queue, playing all {@link AudioInputStream} until
     * the queue is empty or all instances have been played.
     */
    public void resumeQueue() {
        run();
    }

    /**
     * Play the {@link AudioInputStream} last played.
     */
    public void playLast() {
        if (lastPlayed != null) {
            audioQueue.addFirst(lastPlayed);
        }
        resumeQueue();
    }

    @Override
    public void run() {
        try {
            playQueue();
        } catch (AudioPlayblackException e) {
            logger.error("Audio playback error.");
            return;
        }
    }
}