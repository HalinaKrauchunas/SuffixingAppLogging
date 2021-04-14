package org.example;

import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileSuffixApp {

    private static final Logger logger = LogManager.getLogger(FileSuffixApp.class);

    public static void main(String[] args) throws IOException {

        try {
            URL urlLog4j = FileSuffixApp.class.getClassLoader().getResource("log4j.xml");
            DOMConfigurator.configure(urlLog4j);
            logger.info("Application launched");
            logger.debug("Log4j appender configuration was read successfully");
        } catch (NullPointerException e) {
            logger.error("Log4j application config was not read");
        }

        PrinterService printer = new SystemConsolePrinter();

//create object a type ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        File configFile;
        if (args.length > 0) {
            String pathToConfig = args[0];
            configFile = Paths.get(pathToConfig).toFile();
        } else {
            URL res = FileSuffixApp.class.getClassLoader().getResource("config.json");
            String resPath = res.getPath().replaceFirst("/", "");
            configFile = Paths.get(resPath).toFile();
        }
        logger.info("The config.json was successfully read");

//json -> java object(config)
        ApplicationConfig config;
        config = mapper.readValue(configFile, ApplicationConfig.class);
        printer.print("Config: " + config);

//iterate over only files (config.getFiles)
        for (
            String filePathString : config.getFiles()) {
            Path filePath = Paths.get(filePathString);
//check if the file exists
            boolean exists = Files.exists(filePath);
            if (!exists) {
                logger.info("File not exists cannot rename");
            } else {
                printer.print("Old name file:" + filePath.getFileName());
                Path renameToPath = Paths.get(Path.of(filePathString).toAbsolutePath().toString() + config
                    .getSuffix());
                printer.print("New name file: " + renameToPath.getFileName());
//change the file path
                Path.of(filePathString).toFile().renameTo(renameToPath.toFile());
                logger.info("File renamed successfully");
            }
        }
        logger.info("Program completed\n");
    }
}