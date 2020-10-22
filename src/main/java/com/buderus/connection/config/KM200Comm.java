/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.httpclient.DefaultHttpMethodRetryHandler
 *  org.apache.commons.httpclient.HttpClient
 *  org.apache.commons.httpclient.HttpMethod
 *  org.apache.commons.httpclient.methods.ByteArrayRequestEntity
 *  org.apache.commons.httpclient.methods.PostMethod
 *  org.apache.commons.httpclient.methods.RequestEntity
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KM200Comm<KM200BindingProvider> {
    private final Logger logger = LoggerFactory.getLogger(KM200Comm.class);
    private HttpClient client;
    private final KM200Device remoteDevice;
    private Integer maxNbrRepeats;

    public KM200Comm(KM200Device device) {
        this.remoteDevice = device;
        this.maxNbrRepeats = 10;
        if (this.client == null) {
            this.client = new HttpClient();
        }
    }

    public void setMaxNbrRepeats(Integer maxNbrRepeats) {
        this.maxNbrRepeats = maxNbrRepeats;
    }

    /*
     * Exception decompiling
     */
    public byte[] getDataFromService(String service) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 14[CATCHBLOCK]
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:429)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:478)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:728)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:806)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Integer sendDataToService(String service, byte[] data) {
        int rCode = 0;
        PostMethod method = null;
        HttpClient httpClient = this.client;
        synchronized (httpClient) {
            block17: {
                this.logger.debug("Starting send connection...");
                try {
                    block14: for (int i = 0; i < this.maxNbrRepeats && rCode != 204; ++i) {
                        method = new PostMethod("http://" + this.remoteDevice.getIP4Address() + service);
                        method.getParams().setParameter("http.method.retry-handler", (Object)new DefaultHttpMethodRetryHandler(3, false));
                        method.setRequestHeader("Accept", "application/json");
                        method.addRequestHeader("User-Agent", "TeleHeater/2.2.3");
                        method.setRequestEntity((RequestEntity)new ByteArrayRequestEntity(data));
                        rCode = this.client.executeMethod((HttpMethod)method);
                        method.releaseConnection();
                        switch (rCode) {
                            case 204: {
                                continue block14;
                            }
                            case 423: {
                                this.logger.debug("HTTP POST failed: 423, locked, repeating.. ");
                                Thread.sleep(1000L * (long)i + 1L);
                                continue block14;
                            }
                            default: {
                                this.logger.debug("HTTP POST failed: {}", (Object)method.getStatusLine());
                                rCode = 0;
                            }
                        }
                    }
                }
                catch (IOException e) {
                    this.logger.debug("Failed to send data {}", (Throwable)e);
                    if (method != null) {
                        method.releaseConnection();
                    }
                    break block17;
                }
                catch (InterruptedException e) {
                    try {
                        this.logger.debug("Sleep was interrupted: {}", (Object)e.getMessage());
                        break block17;
                    }
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                    finally {
                        if (method != null) {
                            method.releaseConnection();
                        }
                    }
                }
                if (method == null) break block17;
                method.releaseConnection();
            }
            this.logger.debug("Returncode: {}", (Object)rCode);
            return rCode;
        }
    }
}

