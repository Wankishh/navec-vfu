package com.navec.storage;

import java.io.IOException;
import java.io.InputStream;

public interface Storage {
    void putFile(String filename, InputStream stream) throws IOException;
}
