package com.aide.financial.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * 1 File 信息获取
 * 2 File IO操作封装
 */
public class FileUtils {

    private FileUtils(){}

    /**===================================File 序列化/反序列化============================================*/

    // 读取文件
    public static String read(File file){
        StringBuilder result = new StringBuilder();
        try{
            boolean isFile = FileUtils.createFile(file);
            if(isFile){
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s = null;
                while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                    result.append(s + "\n");
                }
                br.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    // 保存文件：如果没有会自动创建，后一个参数是是否追加
    public static void write(File file, String s, boolean isAppend) {
        try {
            boolean isFile = FileUtils.createFile(file);
            if(isFile){
                BufferedWriter bfw = new BufferedWriter(new FileWriter(file, isAppend));
                bfw.write(s);
                bfw.newLine();
                bfw.flush();
                bfw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**===================================File 信息获取============================================*/

    /**
     * 获取文件的字节流
     * @return 如果 IOException，返回空字节数组
     */
    public static byte[] getBytes(@NonNull String absolutePath){
        return getBytes(new File(absolutePath));
    }

    public static byte[] getBytes(@NonNull File file){
        byte[] buffer = new byte[0];
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 获取全路径文件名
     */
    @Nullable
    public static String getFileName(@Nullable File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    @NonNull
    public static String getFileName(@NonNull String absolutePath) {
        if (TextUtils.isEmpty(absolutePath)) return absolutePath;
        int lastSep = absolutePath.lastIndexOf(File.separator);
        return lastSep == -1 ? absolutePath : absolutePath.substring(lastSep + 1);
    }

    /**
     * 获取文件的MD5校验码
     */
    @Nullable
    public static byte[] getFileMD5(@NonNull String absolutePath) throws Exception {
        return getFileMD5(new File(absolutePath));
    }

    @Nullable
    public static byte[] getFileMD5(@Nullable File file) throws Exception {
        if (file == null) return null;
        DigestInputStream dis = null;
        FileInputStream fis = new FileInputStream(file);
        MessageDigest md = MessageDigest.getInstance("MD5");
        dis = new DigestInputStream(fis, md);
        byte[] buffer = new byte[1024 * 256];
        while (true) {
            if (!(dis.read(buffer) > 0)) break;
        }
        md = dis.getMessageDigest();
        dis.close();
        return md.digest();
    }

    @Nullable
    public static String getFileMD5ToString(@NonNull String absolutePath) throws Exception {
        File file = TextUtils.isEmpty(absolutePath) ? null : new File(absolutePath);
        return getFileMD5ToString(file);
    }

    @Nullable
    public static String getFileMD5ToString(File file) throws Exception {
        return bytes2HexString(getFileMD5(file));
    }

    /**
     * 获取文件最后修改的毫秒时间戳
     */
    public static long getFileLastModified(@NonNull String absolutePath) {
        return getFileLastModified(new File(absolutePath));
    }

    public static long getFileLastModified(@Nullable File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    /**
     * 简单获取文件编码格式
     */
    public static String getFileCharsetSimple(@NonNull String absolutePath) throws IOException {
        return getFileCharsetSimple(new File(absolutePath));
    }

    public static String getFileCharsetSimple(@NonNull File file) throws IOException {
        int p = 0;
        InputStream is = null;
        is = new BufferedInputStream(new FileInputStream(file));
        p = (is.read() << 8) + is.read();
        is.close();
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 获取目录大小
     */
    @NonNull
    public static String getDirSize(@NonNull String dirPath) {
        return getDirSize(new File(dirPath));
    }

    @NonNull
    public static String getDirSize(@NonNull File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 获取文件大小（已转成不同单位）
     * @return String
     */
    @NonNull
    public static String getFileSize(@NonNull String absolutePath) {
        return getFileSize(new File(absolutePath));
    }

    @NonNull
    public static String getFileSize(@NonNull File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 获取文件大小
     * @return long
     */
    public static long getFileLength(@NonNull String absolutePath) {
        return getFileLength(new File(absolutePath));
    }

    public static long getFileLength(@NonNull File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    /**
     * 获取目录下所有文件的大小
     */
    public static long getDirLength(@NonNull String dirPath) {
        return getDirLength(new File(dirPath));
    }

    public static long getDirLength(@NonNull File dir) {
        if (!isDirectory(dir)) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**============================================File 操作============================================*/

    /**
     * 判断 File 是否存在
     */
    public static boolean isExists(@NonNull String absolutePath) {
        return isExists(new File(absolutePath));
    }
    
    public static boolean isExists(@Nullable File file) {
        return file != null && file.exists();
    }

    /**
     * 判断是否为文件
     */
    public static boolean isFile(@NonNull String absolutePath) {
        return isFile(new File(absolutePath));
    }

    public static boolean isFile(@NonNull File file) {
        return isExists(file) && file.isFile();
    }

    /**
     * 判断是否为目录
     */
    public static boolean isDirectory(@NonNull String absolutePath) {
        return isDirectory(new File(absolutePath));
    }

    public static boolean isDirectory(@NonNull File dir) {
        return isExists(dir) && dir.isDirectory();
    }

    /**
     * 创建目录
     * @return 是否创建成功
     */
    public static boolean createDirectory(@NonNull String dirPath) {
        return createDirectory(new File(dirPath));
    }
    
    public static boolean createDirectory(@NonNull File file) {
        return isDirectory(file) ? true : file.mkdirs();// 存在，就不用创建（包括上级目录的创建）
    }

    /**
     * 创建文件
     * @return 是否创建成功
     */
    public static boolean createFile(@NonNull String absolutePath) {
        return createFile(new File(absolutePath));
    }
    
    public static boolean createFile(@NonNull File file) {
        if(isFile(file)){
            return true;
        }else{
            // 判断父目录是否存在
            File parentFile = file.getParentFile();
            if(isExists(parentFile)){
                return createNewFile(file);
            }else{
                // 不存在，先创建目录
                if(createDirectory(parentFile)){
                    return createNewFile(file);
                }else{
                    return false;// 创建目录失败
                }
            }
        }
    }

    public static boolean createNewFile(@NonNull File file){
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    // 真实创建文件

    /**
     * 删除文件
     * @return 是否删除成功
     */
    public static boolean deleteFile(@NonNull String absolutePath) throws FileNotFoundException {
        return deleteFile(new File(absolutePath));
    }

    public static boolean deleteFile(@NonNull File file) throws FileNotFoundException {
        if(!isExists(file)){
            throw new FileNotFoundException();
        }
        return file.delete();
    }

    /**
     * 复制或移动目录
     * @param srcDir   源目录
     * @param destDir  目标目录
     * @param isDeleteSrc   是否删除源目录
     * @return 成功与否
     */
    private static boolean copyOrMoveDir(@Nullable File srcDir, @Nullable File destDir, boolean isDeleteSrc) throws IOException {
        if(srcDir == null || destDir == null){
            return false;
        }
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)){
            throw new IOException("can not do this, move directory failed");
        }
        if(!isDirectory(srcDir)){
            throw new FileNotFoundException("have no src directory!");
        }
        // 创建目标目录
        if (createDirectory(destDir)) {
            // 开始移动 (递归)
            File[] files = srcDir.listFiles();
            for (File file : files) {
                File oneDestFile = new File(destPath + file.getName());
                if (file.isFile()) {
                    // 复制或移动文件 如果操作失败返回false
                    if (!copyOrMoveFile(file, oneDestFile, isDeleteSrc)) return false;
                }
                if (file.isDirectory()) {
                    // 递归 复制或移动目录 如果操作失败返回false
                    if (!copyOrMoveDir(file, oneDestFile, isDeleteSrc)) return false;
                }
            }
            // 是否删除
            if (isDeleteSrc) {
                if(deleteDirectory(srcDir)) return false;// 删除旧目录失败
            }
            return true;// 循环完成，则成功
        }else{
            return false;// 创建目标目录失败
        }
    }

    // 复制或移动文件
    private static boolean copyOrMoveFile(@Nullable File srcFile, @Nullable File destFile, boolean isDeleteSrcFile) throws IOException {
        if(srcFile == null || destFile == null){
            return false;
        }
        if(!isFile(srcFile)){
            throw new FileNotFoundException("have no src file!");
        }
        // 创建目标文件
        if (createFile(destFile)) {
            // 开始移动 抛出 IOException
            FileIOUtils.writeFileFromIS(destFile, new FileInputStream(srcFile), false);
            if (isDeleteSrcFile) {// 删除旧文件
                if(!deleteFile(srcFile)) return false;// 失败
            }
            return true;
        }else{
            return false;// 创建目标文件失败
        }
    }

    /**
     * 复制目录
     */
    public static boolean copyDir(@NonNull String srcDirPath, @NonNull String destDirPath) throws IOException {
        return copyDir(new File(srcDirPath), new File(destDirPath));
    }

    public static boolean copyDir(File srcDir, File destDir) throws IOException {
        return copyOrMoveDir(srcDir, destDir, false);
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(@NonNull String absolutePath, @NonNull String destFilePath) throws IOException {
        return copyFile(new File(absolutePath), new File(destFilePath));
    }

    public static boolean copyFile(File srcFile, File destFile) throws IOException {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动目录
     */
    public static boolean moveDir(@NonNull String srcDirPath, @NonNull String destDirPath) throws IOException {
        return moveDir(new File(srcDirPath), new File(destDirPath));
    }

    public static boolean moveDir(File srcDir, File destDir) throws IOException {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * 移动文件
     */
    public static boolean moveFile(@NonNull String absolutePath, @NonNull String destFilePath) throws IOException {
        return moveFile(new File(absolutePath), new File(destFilePath));
    }

    public static boolean moveFile(File srcFile, File destFile) throws IOException {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    /**
     * 递归删除目录
     * @return 是否删除成功
     * @throws FileNotFoundException gai该目录路径 Directory 不存在
     */
    public static boolean deleteDirectory(@NonNull String dirPath) throws FileNotFoundException {
        return deleteDirectory(new File(dirPath));
    }

    public static boolean deleteDirectory(@NonNull String dirPath, @NonNull FileFilter filter) throws FileNotFoundException {
        return deleteDirectory(new File(dirPath), filter);
    }

    public static boolean deleteDirectory(@NonNull File dir) throws FileNotFoundException {
        return deleteDirectory(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteDirectory(@NonNull File dir, @NonNull FileFilter filter) throws FileNotFoundException {
        if(!isDirectory(dir)){
            throw new FileNotFoundException("have no this directory");
        }
        // 存在，就递归删除
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    }
                    // 递归删除
                    if (file.isDirectory()) {
                        if (!deleteDirectory(file, filter)) return false;
                    }
                }
            }
        }
        return dir.delete();// 最后删除该层的目录
    }

    /**
     * 获取目录下所有文件
     * @return 目录下所有文件 (默认不递归)
     */
    @NonNull
    public static ArrayList<File> listFilesInDir(@NonNull String dirPath) {
        return listFilesInDir(dirPath, false);
    }
    
    @NonNull
    public static ArrayList<File> listFilesInDir(@NonNull String dirPath, boolean isRecursive) {
        return listFilesInDir(new File(dirPath), isRecursive);
    }
    
    @NonNull
    public static ArrayList<File> listFilesInDir(@NonNull File dir) {
        return listFilesInDir(dir, false);
    }
    
    @NonNull
    public static ArrayList<File> listFilesInDir(@NonNull File dir, boolean isRecursive) { // isRecursive 是否递归
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;// 默认不过滤
            }
        }, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件
     * @param filter 过滤条件
     * @return 目录下所有过滤后的文件 (默认不递归)
     */
    @NonNull
    public static ArrayList<File> listFilesInDirWithFilter(@NonNull String dirPath, @NonNull FileFilter filter) {
        return listFilesInDirWithFilter(new File(dirPath), filter, false);
    }

    @NonNull
    public static ArrayList<File> listFilesInDirWithFilter(@NonNull String dirPath, @NonNull FileFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(new File(dirPath), filter, isRecursive);
    }

    @NonNull
    public static ArrayList<File> listFilesInDirWithFilter(@NonNull File dir, @NonNull FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    @NonNull
    public static ArrayList<File> listFilesInDirWithFilter(@NonNull File dir, @NonNull FileFilter filter, boolean isRecursive) {
        ArrayList<File> list = new ArrayList<>();
        if (!isDirectory(dir)) return list;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                // 根据创建的 FileFilter 重写 accept 接受的规则，来过滤
                if (filter.accept(file)) {
                    list.add(file);
                }
                // 如果 File 为 Directory，并且需要递归
                if (isRecursive && file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**========================================辅助不常用方法========================================*/

    @NonNull
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * byteArr转hexString
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    @Nullable
    private static String bytes2HexString(@Nullable byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 字节数转合适内存大小
     * <p>保留2位小数</p>
     */
    private static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format("%.2fB", (double) byteNum);
        } else if (byteNum < (1024 * 1024)) {
            return String.format("%.2fKB", (double) byteNum / 1024);
        } else if (byteNum < (1024 * 1024 * 1024)) {
            return String.format("%.2fMB", (double) byteNum / (1024 * 1024));
        } else {
            return String.format("%.2fGB", (double) byteNum / (1024 * 1024 * 1024));
        }
    }

    /**
     * 获取不带拓展名的文件名
     */
    @Nullable
    public static String getFileNameNoExtension(@Nullable File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    @NonNull
    public static String getFileNameNoExtension(@NonNull String absolutePath) {
        if (TextUtils.isEmpty(absolutePath)) return absolutePath;
        int lastPoi = absolutePath.lastIndexOf('.');
        int lastSep = absolutePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? absolutePath : absolutePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return absolutePath.substring(lastSep + 1);
        }
        return absolutePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 获取全路径中的文件拓展名
     */
    @Nullable
    public static String getFileExtension(@Nullable File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    @NonNull
    public static String getFileExtension(@NonNull String absolutePath) {
        if (TextUtils.isEmpty(absolutePath)) return absolutePath;
        int lastPoi = absolutePath.lastIndexOf('.');
        int lastSep = absolutePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return absolutePath.substring(lastPoi + 1);
    }

}
