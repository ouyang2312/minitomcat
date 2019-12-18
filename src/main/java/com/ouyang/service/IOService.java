package com.ouyang.service;

import java.io.IOException;

public interface IOService {

    void start();

    void handle() throws IOException;
}
