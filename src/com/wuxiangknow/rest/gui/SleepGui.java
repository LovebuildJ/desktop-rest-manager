package com.wuxiangknow.rest.gui;

import com.wuxiangknow.rest.config.RestConfig;
import com.wuxiangknow.rest.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Desciption 休息界面
 * @Author WuXiang
 * @Date 2019/1/11 21:42
 */
public class SleepGui extends JFrame{

    private JLabel imageLabel;//图片

    private long timeSleeped = 0;

    public SleepGui(SettingGui settingGui) throws HeadlessException {
        this.setLayout(null);
        this.setResizable(false);//不可改变大小
        this.setUndecorated(true);//无标题栏
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);//最大化
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        imageLabel = new JLabel();
        BufferedImage bufferedImage = null;
        if (settingGui !=null && settingGui.getSleepImagePath() != null) {
            try {
                //获取该路径
                java.util.List<String> resource = getResource(settingGui.getSleepImagePath());
                if(resource.size()>1){
                    String path = getFileByRandom(resource);
                    bufferedImage = ImageIO.read(new File(path));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bufferedImage == null) {
            try {
                java.util.List<String> resource = getResource(RestConfig.SLEEP_IMAGE_DIR);
                String path = getFileByRandom(resource);
                bufferedImage = ImageIO.read(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        imageLabel.setIcon(new ImageIcon(ImageUtil.getScaledImage(bufferedImage,(int) screenSize.getWidth(), (int) screenSize.getHeight())));
        imageLabel.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(KeyEvent.VK_ESCAPE == e.getKeyCode()){
                    synchronized (SleepGui.class){
                        timeSleeped =Long.MAX_VALUE;
                    }
                }
            }
        });
        this.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.add(imageLabel);
        this.setVisible(true);
        if(!this.requestFocusInWindow()){
            this.requestFocus();
        }
        if(settingGui!=null){
            while (true){
                try {
                    Thread.sleep(500);
                    synchronized (SleepGui.class){
                        if(timeSleeped < settingGui.getRestTime()){
                            timeSleeped += 500;
                        }else{
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getFileByRandom(List<String> resource) {
        Random random = new Random();
        int i = random.nextInt(resource.size());
        return resource.get(i);
    }

    public java.util.List<String> getResource(String path) {
        URL resource = this.getClass().getResource(path);
        String type = resource.getProtocol();
        if (type.equals("jar")) {
            return getResourceByJar(resource);
        }else{
            return getResourceByFile(resource);
        }
    }

    public List<String> getResourceByJar(URL resource) {
        ArrayList<String> files = new ArrayList<>();
        String path = resource.getPath();
        String[] jarInfo = path.split("!");
        //之前就是jar包路径
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if(name.startsWith(packagePath) && ImageUtil.isImage(name) ){
                    files.add(jarFilePath.concat("!/").concat(name));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public java.util.List<String> getResourceByFile(URL resource) {
        ArrayList<String> fileNames = new ArrayList<>();
        File file = new File(resource.getPath());
        if(file.exists() && file.isDirectory()){
            String name;
            for (File childFile : file.listFiles()) {
                name = childFile.getName();
                if(childFile.isFile() && ImageUtil.isImage(name)){
                    fileNames.add(childFile.getPath());
                }
            }
        }
        return fileNames;
    }
}