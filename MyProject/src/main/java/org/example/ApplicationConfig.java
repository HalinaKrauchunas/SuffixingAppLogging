package org.example;

import java.util.*;

public class ApplicationConfig {

    private List<String> files;
    private String suffix;

    public List<String> getFiles() {

        return files;
    }

    public String getSuffix() {

        return suffix;
    }

    public ApplicationConfig(List<String> files, String suffix) {

        this.files = files;
        this.suffix = suffix;
    }

    public ApplicationConfig() {

    }

    @Override
    public String toString() {

        return "ApplicationConfig{" +
            "files=" + files +
            ", suffix='" + suffix + '\'' +
            '}';
    }
}
