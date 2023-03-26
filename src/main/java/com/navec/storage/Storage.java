package com.navec.storage;

import java.io.InputStream;

public interface Storage {
    void putFile(String filename, InputStream stream);
}
