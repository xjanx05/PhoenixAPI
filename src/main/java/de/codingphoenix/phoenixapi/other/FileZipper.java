package de.codingphoenix.phoenixapi.other;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZipper {
    private static final int BUFFER_SIZE = 4096;
    protected File zipFile;

    public FileZipper(File zipFile) {
        this.zipFile = zipFile;
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        File oldFile = new File(filePath);
        if (oldFile.exists()) {
            oldFile.delete();
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        boolean var6 = false;

        int read;
        while((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }

        bos.close();
    }

    private void zipFile(File file, ZipOutputStream outputStream, Path root) {
        if (!file.getName().endsWith(".zip")) {
            try {
                Path orgPath = Paths.get(file.getPath());
                Path zipFilePath = root.relativize(orgPath);
                outputStream.putNextEntry(new ZipEntry(zipFilePath.toString()));
                byte[] buffer = Files.readAllBytes(orgPath);
                outputStream.write(buffer, 0, buffer.length);
                outputStream.closeEntry();
            } catch (Exception var7) {
                var7.printStackTrace();
            }

        }
    }

    private void zipFolder(File file, ZipOutputStream outputStream, Path root) {
        File[] var4 = file.listFiles();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            File toZip = var4[var6];
            if (toZip.isFile()) {
                this.zipFile(toZip, outputStream, root);
            } else {
                this.zipFolder(toZip, outputStream, root);
            }
        }

    }

    public void zip(ArrayList<File> files, Path rootFrom) {
        try {
            File file1 = new File(this.zipFile.getParent());
            if (!file1.isDirectory()) {
                file1.mkdirs();
            }

            if (!this.zipFile.exists()) {
                this.zipFile.createNewFile();
            }
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        String zipFileName = this.zipFile.getPath();

        try {
            FileOutputStream fileoutputStream = null;
            ZipOutputStream outputStream = new ZipOutputStream(fileoutputStream = new FileOutputStream(zipFileName));
            Iterator var6 = files.iterator();

            while(var6.hasNext()) {
                File toZip = (File)var6.next();
                if (toZip.isFile()) {
                    this.zipFile(toZip, outputStream, rootFrom);
                } else {
                    this.zipFolder(toZip, outputStream, rootFrom);
                }
            }

            outputStream.close();
            fileoutputStream.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    public boolean unzip(String destDirectory) {
        try {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }

            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(this.zipFile));

            for(ZipEntry entry = zipIn.getNextEntry(); entry != null; entry = zipIn.getNextEntry()) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    this.extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }

                zipIn.closeEntry();
            }

            zipIn.close();
            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }

    public File getZipFile() {
        return this.zipFile;
    }
}
