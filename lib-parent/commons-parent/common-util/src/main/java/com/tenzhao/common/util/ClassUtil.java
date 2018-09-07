package com.tenzhao.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 获取指定包下的所有Class
 * @author chenxj
 *
 */
public class ClassUtil {
	/**
	 * 获得包下面的所有的class
	 * 
	 * @param pack
	 *            package完整名称  格式："com.uhope.hzz.dataimp"
	 * @return List包含所有class的实例
	 */
    public static Set<Class<?>> getClasssFromPackage(String pack) {
    	// 第一个class类的集合
    			Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
    			// 是否循环迭代
    			boolean recursive = true;
    			// 获取包的名字 并进行替换
    			String packageName = pack;
    			String packageDirName = packageName.replace('.', '/');
    			// 定义一个枚举的集合 并进行循环来处理这个目录下的things
    			Enumeration<URL> dirs;
    			try {
    				dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
    				// 循环迭代下去
    				while (dirs.hasMoreElements()) {
    					// 获取下一个元素
    					URL url = dirs.nextElement();
    					// 得到协议的名称
    					String protocol = url.getProtocol();
    					// 如果是以文件的形式保存在服务器上
    					if ("file".equals(protocol)) {
    						System.err.println("file类型的扫描");
    						// 获取包的物理路径
    						String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
    						// 以文件的方式扫描整个包下的文件 并添加到集合中
    						findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
    					} else if ("jar".equals(protocol)) {
    						// 如果是jar包文件
    						// 定义一个JarFile
    						System.err.println("jar类型的扫描");
    						JarFile jar;
    						try {
    							// 获取jar
    							jar = ((JarURLConnection) url.openConnection()).getJarFile();
    							// 从此jar包 得到一个枚举类
    							Enumeration<JarEntry> entries = jar.entries();
    							// 同样的进行循环迭代
    							while (entries.hasMoreElements()) {
    								// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
    								JarEntry entry = entries.nextElement();
    								String name = entry.getName();
    								// 如果是以/开头的
    								if (name.charAt(0) == '/') {
    									// 获取后面的字符串
    									name = name.substring(1);
    								}
    								// 如果前半部分和定义的包名相同
    								if (name.startsWith(packageDirName)) {
    									int idx = name.lastIndexOf('/');
    									// 如果以"/"结尾 是一个包
    									if (idx != -1) {
    										// 获取包名 把"/"替换成"."
    										packageName = name.substring(0, idx).replace('/', '.');
    									}
    									// 如果可以迭代下去 并且是一个包
    									if ((idx != -1) || recursive) {
    										// 如果是一个.class文件 而且不是目录
    										if (name.endsWith(".class") && !entry.isDirectory()) {
    											// 去掉后面的".class" 获取真正的类名
    											String className = name.substring(packageName.length() + 1, name.length() - 6);
    											try {
    												// 添加到classes
    												classes.add(Class.forName(packageName + '.' + className));
    											} catch (ClassNotFoundException e) {
    												// log
    												// .error("添加用户自定义视图类错误
    												// 找不到此类的.class文件");
    												e.printStackTrace();
    											}
    										}
    									}
    								}
    							}
    						} catch (IOException e) {
    							// log.error("在扫描用户定义视图时从jar包获取文件出错");
    							e.printStackTrace();
    						}
    					}
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}

    			return classes;
	}

	/**
	 * 在package对应的路径下找到所有的class
	 * 
	 * @param packageName
	 *            package名称
	 * @param filePath
	 *            package对应的路径
	 * @param recursive
	 *            是否查找子package
	 * @param clazzs
	 *            找到class以后存放的集合
	 */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive,
			Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(
							Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 从jar文件中读取指定目录下面的所有的class文件
	 * 
	 * @param jarPaht
	 *            jar文件存放的位置  格式：D:/Tomcats/webapps/工程上下文/WEB-INF/lib/xmlbeans-2.3.0.jar"
	 * @param filePaht
	 *            指定的文件目录  格式："org/w3c/dom"
	 * @return 所有的的class的对象
	 */
    public static List<Class<?>> getClasssFromJarFile(String jarPaht, String filePaht) {
		List<Class<?>> clazzs = new ArrayList<Class<?>>();

		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPaht);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<JarEntry> jarEntryList = new ArrayList<JarEntry>();

		Enumeration<JarEntry> ee = jarFile.entries();
		while (ee.hasMoreElements()) {
			JarEntry entry = (JarEntry) ee.nextElement();
			// 过滤我们出满足我们需求的东西
			if (entry.getName().startsWith(filePaht) && entry.getName().endsWith(".class")) {
				jarEntryList.add(entry);
			}
		}
		for (JarEntry entry : jarEntryList) {
			String className = entry.getName().replace('/', '.');
			className = className.substring(0, className.length() - 6);

			try {
				clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(className));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return clazzs;
	}
	
	/**
	 * 通过流将jar中的一个文件的内容输出
	 * 
	 * @param jarPaht
	 *            jar文件存放的位置  格式：D:/Tomcats/webapps/工程上下文/WEB-INF/lib/xmlbeans-2.3.0.jar"
	 * @param filePaht
	 *            指定的文件  格式："org/w3c/dom/UserDataHandler.class"
	 */
	public static void getStream(String jarPaht, String filePaht) {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPaht);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Enumeration<JarEntry> ee = jarFile.entries();

		List<JarEntry> jarEntryList = new ArrayList<JarEntry>();
		while (ee.hasMoreElements()) {
			JarEntry entry = (JarEntry) ee.nextElement();
			// 过滤我们出满足我们需求的东西，这里的fileName是指向一个具体的文件的对象的完整包路径，比如com/mypackage/test.txt
			if (entry.getName().startsWith(filePaht)) {
				jarEntryList.add(entry);
			}
		}
		try {
			InputStream in = jarFile.getInputStream(jarEntryList.get(0));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s = "";

			while ((s = br.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
