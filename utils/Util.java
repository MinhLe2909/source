package tamhoang.ldpro4.utils;

import android.os.Environment;
import android.util.Log;

import net.lingala.zip4j.util.InternalZipConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Util {
    static final int BUFFER_SIZE = 2048;
    public static String DIRECTORY_PATH = Environment.getExternalStorageDirectory() + File.separator + "ldpro_logs.txt";
    public static boolean ON = false;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void checkFileSize() {
        try {
            File file = new File(DIRECTORY_PATH);
            if ((file.length() / 1024) / 1024 > 5) {
                file.renameTo(new File(Environment.getExternalStorageDirectory() + File.separator + "ldpro_logs_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".txt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        try {
            FileChannel sourceChannel;
            FileChannel destChannel;
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            sourceChannel = new FileInputStream(sourceFile).getChannel();
            destChannel = new FileInputStream(destFile).getChannel();

            destChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
            if (sourceChannel != null) {
                sourceChannel.close();
            }
            if (destChannel != null) {
                destChannel.close();
            }
        } catch (Throwable th) {
        }
    }

    public static void unzip(String zipFile, String location) throws IOException {
        try {
            File file = new File(location);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            while (true) {
                try {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        zipInputStream.close();
                        return;
                    }
                    String str = location + nextEntry.getName();
                    if (nextEntry.isDirectory()) {
                        File file2 = new File(str);
                        if (!file2.isDirectory()) {
                            file2.mkdirs();
                        }
                    } else {
                        FileOutputStream fileOutputStream = new FileOutputStream(str, false);
                        while (true) {
                            try {
                                int read = zipInputStream.read();
                                if (read == -1) {
                                    break;
                                } else {
                                    fileOutputStream.write(read);
                                }
                            } catch (Throwable th) {
                                fileOutputStream.close();
                                throw th;
                            }
                        }
                        zipInputStream.closeEntry();
                        fileOutputStream.close();
                    }
                } catch (Throwable th2) {
                    zipInputStream.close();
                    throw th2;
                }
            }
        } catch (Exception unused) {
        }
    }

    public static void writeLog(Exception ex) {
        if (ON) {
            checkFileSize();
            String format = formatter.format(new Date());
            try {
                String stackTraceString = Log.getStackTraceString(ex);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(DIRECTORY_PATH), true), StandardCharsets.UTF_8));
                bufferedWriter.append((CharSequence) format).append((CharSequence) ":\n");
                bufferedWriter.append((CharSequence) stackTraceString).append((CharSequence) "\n");
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeLogInfo(String info) {
        if (ON) {
            checkFileSize();
            String format = formatter.format(new Date());
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DIRECTORY_PATH, true), StandardCharsets.UTF_8));
                bufferedWriter.append(format).append(":").append(info).append("\n");
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeWinWinLog(Exception ex) {
        String format = formatter.format(new Date());
        try {
            String stackTraceString = Log.getStackTraceString(ex);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(DIRECTORY_PATH), true), StandardCharsets.UTF_8));
            bufferedWriter.append(format).append(":\n");
            bufferedWriter.append(stackTraceString).append("\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zip(String[] files, String zipFile) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte[] bArr = new byte[2048];
            for (int i = 0; i < files.length; i++) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(files[i]), 2048);
                zipOutputStream.putNextEntry(new ZipEntry(files[i].substring(files[i].lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1)));
                while (true) {
                    int read = bufferedInputStream.read(bArr, 0, 2048);
                    if (read != -1) {
                        zipOutputStream.write(bArr, 0, read);
                    }
                }
            }
        } finally {
            zipOutputStream.close();
        }
    }
}
