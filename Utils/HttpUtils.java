package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class HttpUtils {
    public static boolean isRequestEnd(byte[] request, int length) {
        if (length < 4) {
            return false;
        }
        return request[length - 4] == '\r' &&
                request[length - 3] == '\n' &&
                request[length - 2] == '\r' &&
                request[length - 1] == '\n';
    }

    public static byte[] readRequestFully(InputStream in) throws IOException {
        byte[] buff = new byte[8192];
        int headerLen = 0;
        while (true) {
            int count = in.read(buff, headerLen, buff.length - headerLen);
            if (count < 0) {
                throw new RuntimeException("Incoming connection");
            } else headerLen += count;
            if (isRequestEnd(buff, headerLen)) {
                return Arrays.copyOfRange(buff, 0, headerLen);
            }
            if (headerLen == buff.length) {
                throw new RuntimeException("To big HTTP");
            }
        }
    }

    public static byte[] createResponse(String text) throws IOException {
        String html = ""
                + "<html>"
                + "<body>"
                + "<p style=\"color: #0000FF;font-size: 48 pt\">"
                + text
                + "</p>"
                + "</body>"
                + "</html>";
        return new byte[0];
    }
}