package com.songming.sanitation.frameset.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * @Title: SDcardUtils.java
 * @Package
 * @Description: TODO
 * @date 2014年6月17日
 * @version V1.0 </br>在SDCard中创建与删除文件权限
 * <pre>
 * &lt;uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/&gt;
 * </pre>
 * </br> 往SDCard写入数据权限
 * <pre>
 * &lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;
 * </pre>
 */
public class SDcardUtils {
	
	static final int ERROR = -1;

	/**
	 * SD卡是否存在
	 * */
	public static boolean existSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}
	
	/**
	 * 
	 * SD卡剩余空间
	 * */
	public static long getSDFreeSize(){  
	     //取得SD卡文件路径  
	     File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //空闲的数据块的数量  
	     long freeBlocks = sf.getAvailableBlocks();  
	     //返回SD卡空闲大小  
	     return freeBlocks * blockSize;  //单位Byte  
	     //return (freeBlocks * blockSize)/1024;   //单位KB  
	     //return (freeBlocks * blockSize)/1024 /1024; //单位MB  
	   }   
	
	/**
	 * 
	 * SD卡总容量
	 * */
	public static long getSDAllSize(){  
	     //取得SD卡文件路径  
	     File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //获取所有数据块数  
	     long allBlocks = sf.getBlockCount();  
	     //返回SD卡大小  
	     return allBlocks * blockSize; //单位Byte  
	     //return (allBlocks * blockSize)/1024; //单位KB  
	     //return (allBlocks * blockSize)/1024/1024; //单位MB  
	   }      
	
	/**
	 * 
	 * 指定文件目录总容量
	 * */
	public static long getFileAllSize(File path){  
	     //取得SD卡文件路径  
	    // File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //获取所有数据块数  
	     long allBlocks = sf.getBlockCount();  
	     //返回SD卡大小  
	     return allBlocks * blockSize; //单位Byte  
	     //return (allBlocks * blockSize)/1024; //单位KB  
	     //return (allBlocks * blockSize)/1024/1024; //单位MB  
	   }      
	
	
	/**
	 * 
	 * 指定文件目录剩余空间
	 * */
	public static long getFileDirFreeSize(File path){  
	     //取得SD卡文件路径  
	     //File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //空闲的数据块的数量  
	     long freeBlocks = sf.getAvailableBlocks();  
	     //返回SD卡空闲大小  
	     return freeBlocks * blockSize;  //单位Byte  
	     //return (freeBlocks * blockSize)/1024;   //单位KB  
	     //return (freeBlocks * blockSize)/1024 /1024; //单位MB  
	   }   

    /**
     * 外部存储是否可用
     * @return
     */
    static public boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部可用空间大小
     * @return
     */
    static public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部空间大小
     * @return
     */
    static public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机外部可用空间大小
     * @return
     */
    static public long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取手机外部空间大小
     * @return
     */
    static public long getTotalExternalMemorySize() {
    	
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    public  static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "K";
            size /= 1024;
            if (size >= 1024) {
                suffix = "M";
                size /= 1024;
            }
        }else{
        	suffix="b";
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
}
