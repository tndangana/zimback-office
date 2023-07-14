package zw.co.zimttech.abn.key;

import org.apache.commons.lang3.StringUtils;
import zw.co.zimttech.abn.exceptions.BusinessValidationException;

import java.util.Random;
import java.util.UUID;


public interface KeyGen {

    static long getUniqueId() {
        long l = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            builder.append((int) (Math.random() * 10));
        }
        int randomNumber = (int) (Math.random() * 1000);
        String entityId = l + builder.toString() + randomNumber;
        try {
            l = Long.parseLong(entityId);
        } catch (NumberFormatException e) {
            return l;
        }
        return l;
    }

    static String generateKey() {
        return UUID.randomUUID().toString().replace("-", StringUtils.EMPTY) + new Random().nextInt(9999);
    }

    static long getTrace() {
        return KeyGen.getUniqueId() % 100000;
    }

    static long getUniqueId(int length) {
        if (length <= 0) {
            throw new BusinessValidationException("Invalid Key length");
        }
        long currentTime = System.currentTimeMillis();

        if (String.valueOf(currentTime).length() == length) {
            return currentTime;
        }

        if (String.valueOf(currentTime).length() > length) {
            currentTime = currentTime % 1000;
        }

        StringBuilder builder = new StringBuilder(String.valueOf(currentTime));
        int remaining = length - String.valueOf(currentTime).length();
        for (int i = 0; i < remaining; i++) {
            int nxt = new Random().nextInt(10);
            builder.append(nxt);
        }
        String entityId = builder.toString();
        try {
            currentTime = Long.parseLong(entityId);
        } catch (NumberFormatException e) {
            return currentTime;
        }
        return currentTime;
    }

}
