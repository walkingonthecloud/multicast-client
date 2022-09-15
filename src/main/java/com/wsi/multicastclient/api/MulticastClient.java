package com.wsi.multicastclient.api;

import java.io.IOException;

public interface MulticastClient {

    void receiveMessage() throws IOException;

}
