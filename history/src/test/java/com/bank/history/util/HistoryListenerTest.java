package com.bank.history.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HistoryListenerTest {

    HistoryListener listener;


    @Test
    void newRevisionSuccess() {

        listener = mock(HistoryListener.class);
        Object revInfo1 = mock(HistoryRevInfo.class);

        doAnswer(invocation -> {

            final HistoryRevInfo revInfo = invocation.getArgument(0);
            revInfo.setUsername("Freeman");

            assertThat(revInfo).isNotNull();
            verify(revInfo).setUsername("Freeman");

            return null;

        }).when(listener).newRevision(revInfo1);

        listener.newRevision(revInfo1);
    }

    @Test
    void newRevisionFail() {
        listener = new HistoryListener();
        assertThrows(RuntimeException.class, () -> listener.newRevision(1));
    }
}