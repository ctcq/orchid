package org.ctcq.orchid.model.media;

/**
 * All HTML tags that may be supported by endpoint drivers.
 * 
 * All these tags MAY be supported by {@link org.ctcq.orchid.driver.EndpointDriver}.
 * If not supported by a driver, the driver MUST NOT use it. 
 */
public enum HtmlTag {
    // Structucal
    HTML("html"), HEAD("head"), BODY("body"),

    // Header
    META("meta"),
    // Body tags
    H1("h1"), H2("h2"),

    P("p"),
    ;

    public final String label;

    private HtmlTag(String label) {
        this.label = label;
    }
}