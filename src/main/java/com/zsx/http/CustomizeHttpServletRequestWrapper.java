package com.zsx.http;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Slf4j
public class CustomizeHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buffer;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CustomizeHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        log.info("=========CustomizeHttpServletRequestWrapper.CustomizeHttpServletRequestWrapper()=========");
        try {
            InputStream is = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff =new byte[1024];
            int read = 0;
            while ((read = is.read(buff)) >0) {
                baos.write(buff,0, read);
                this.buffer = baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        log.info("=========CustomizeHttpServletRequestWrapper.getInputStream()=========");
        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

        return new ServletInputStream() {

            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                //do nothing
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        log.info("=========CustomizeHttpServletRequestWrapper.getReader()=========");
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
