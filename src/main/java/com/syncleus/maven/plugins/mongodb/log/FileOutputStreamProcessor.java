/**
 * Copyright 2015 Syncleus, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
Copyright 2015 Syncleus, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.syncleus.maven.plugins.mongodb.log;

import de.flapdoodle.embed.process.io.IStreamProcessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileOutputStreamProcessor implements IStreamProcessor {

    private static OutputStreamWriter stream;

    private String logFile;
    private String encoding;

    public FileOutputStreamProcessor(final String logFile, final String encoding) {
        setLogFile(logFile);
        setEncoding(encoding);
    }

    @Override
    public synchronized void process(final String block) {
        try {

            if (stream == null) {
                stream = new OutputStreamWriter(new FileOutputStream(logFile), encoding);
            }

            stream.write(block);
            stream.flush();

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onProcessed() {
        process("\n");
    }

    private void setLogFile(final String logFile) {
        if (logFile == null || logFile.trim().length() == 0) {
            throw new IllegalArgumentException("no logFile given");
        }
        this.logFile = logFile;
    }

    private void setEncoding(final String encoding) {
        if (encoding == null || encoding.trim().length() == 0) {
            throw new IllegalArgumentException("no encoding given");
        }
        this.encoding = encoding;
    }
}
