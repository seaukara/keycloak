/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.testsuite;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class Retry {

    public static void execute(Runnable runnable, int retryCount, long intervalMillis) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (RuntimeException | AssertionError e) {
                retryCount--;
                if (retryCount > 0) {
                    try {
                        Thread.sleep(intervalMillis);
                    } catch (InterruptedException ie) {
                        ie.addSuppressed(e);
                        throw new RuntimeException(ie);
                    }
                } else {
                    throw e;
                }
            }
        }
    }

}
