package com.zsx.design.pattern.behavioral.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTest {

    @Test
    void testPublish() {
        State draft = new Draft(new Document());
        draft.render();
        draft.publish();
        assertEquals("Draft-publish", draft.publish());

        State moderation = new Moderation(new Document());
        moderation.render();
        moderation.publish();
        assertEquals("Moderation-publish", moderation.publish());

        State published = new Published(new Document());
        published.render();
        published.publish();
        assertEquals("Published-publish", published.publish());
    }
}
