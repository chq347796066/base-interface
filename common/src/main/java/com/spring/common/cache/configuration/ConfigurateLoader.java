package com.spring.common.cache.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * 解析缓存配置文件抽象类
 */
public abstract class ConfigurateLoader<T> {

	private static final Log logger = LogFactory
			.getLog(ConfigurateLoader.class);

	String filepath;
	
	InputStream inputStream;

	String cacheName = "";

	public void setFilePath(String filepath) {
		this.filepath = filepath;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getCacheName() {
		return this.cacheName;
	}

	/**
	 *	加载保存缓存实例入口 
	 */
	public void execute() {
		putCache(loadCacheItems());
	}

	protected abstract Map<Object, T> loadCacheItems();

	protected abstract void putCache(Map<Object, T> cacheItems);

	protected static class XmlConfig {

		/**
		 * 解析缓存配置文件
		 */
		protected static Map<String, CacheConfig> readConfig(String filename,
				Class<?> clz) {

			Map<String, CacheConfig> cacheConfigs = new HashMap<String, CacheConfig>();
			InputStream stream = null;
			logger.info("加载缓存信息" + "配置信息加载组件,读取配置文件中初始化加载的系统数据.");
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = factory.newDocumentBuilder();
				stream = new FileInputStream(new File(filename));
				Document doc = docBuilder.parse(stream);
				Node root = doc.getFirstChild();
				NodeList list = root.getChildNodes();
				for (int j = 0; j < list.getLength(); j++) {
					Node node = list.item(j);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						CacheConfig config = new CacheConfig();
						String name = readAttributeValue(node, "name");
						String cacheName = readAttributeValue(node, "cacheName");
						String daoInterface = readAttributeValue(node,
								"daoInterface");
						String primaryKey = readAttributeValue(node,
								"primaryKey");
						String method = readAttributeValue(node, "method");
						String timeToIdleSeconds = readAttributeValue(node,
								"timeToIdleSeconds");
						String timeToLiveSeconds = readAttributeValue(node,
								"timeToLiveSeconds");
						config.setCacheName(cacheName);
						config.setDaoInterface(daoInterface);
						config.setMethod(method);
						config.setPrimaryKey(primaryKey);
						config.setTimeToIdleSeconds(""
								.equals(timeToIdleSeconds) ? 0 : Integer
								.parseInt(timeToIdleSeconds));
						config.setTimeToLiveSeconds(""
								.equals(timeToLiveSeconds) ? 0 : Integer
								.parseInt(timeToLiveSeconds));
						cacheConfigs.put(cacheName + name, config);
					}
				}
				return cacheConfigs;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != stream)
						stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		
		/**
		 * 解析缓存配置文件
		 */
		protected static Map<String, CacheConfig> readConfig(InputStream inputStream,
				Class<?> clz) {

			Map<String, CacheConfig> cacheConfigs = new HashMap<String, CacheConfig>();
			InputStream stream = null;
			logger.info("加载缓存信息" + "配置信息加载组件,读取配置文件中初始化加载的系统数据.");
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = factory.newDocumentBuilder();
				Document doc = docBuilder.parse(inputStream);
				Node root = doc.getFirstChild();
				NodeList list = root.getChildNodes();
				for (int j = 0; j < list.getLength(); j++) {
					Node node = list.item(j);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						CacheConfig config = new CacheConfig();
						String name = readAttributeValue(node, "name");
						String cacheName = readAttributeValue(node, "cacheName");
						String daoInterface = readAttributeValue(node,
								"daoInterface");
						String primaryKey = readAttributeValue(node,
								"primaryKey");
						String method = readAttributeValue(node, "method");
						String timeToIdleSeconds = readAttributeValue(node,
								"timeToIdleSeconds");
						String timeToLiveSeconds = readAttributeValue(node,
								"timeToLiveSeconds");
						config.setCacheName(cacheName);
						config.setDaoInterface(daoInterface);
						config.setMethod(method);
						config.setPrimaryKey(primaryKey);
						config.setTimeToIdleSeconds(""
								.equals(timeToIdleSeconds) ? 0 : Integer
								.parseInt(timeToIdleSeconds));
						config.setTimeToLiveSeconds(""
								.equals(timeToLiveSeconds) ? 0 : Integer
								.parseInt(timeToLiveSeconds));
						cacheConfigs.put(cacheName + name, config);
					}
				}
				return cacheConfigs;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != stream)
						stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		private static String readAttributeValue(final Node node,
				final String name) {
			String value = "";
			if (node.getAttributes().getNamedItem(name) != null) {
				return node.getAttributes().getNamedItem(name).getNodeValue();
			}
			return value;
		}

		/**
		 * 解析索引配置文件
		 */
		protected static Map<String, IndexModel> readIndex(String filename,
				Class<?> clz) {
			Map<String, IndexModel> indexes = new HashMap<String, IndexModel>();
			InputStream stream = null;
			logger.info("加载缓存信息" + "配置信息加载组件, 读取配置文件中的缓存索引配置数据.");
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = factory.newDocumentBuilder();
				stream = new FileInputStream(new File(filename));
				Document doc = docBuilder.parse(stream);

				Node root = doc.getFirstChild();

				NodeList list = root.getChildNodes();
				for (int j = 0; j < list.getLength(); j++) {
					Node node = list.item(j);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IndexModel indexModel = new IndexModel();
						String entityName = readAttributeValue(node,
								"entityName");
						String cacheName = readAttributeValue(node, "cacheName");

						indexModel.setEntityName(entityName);
						indexModel.setCacheName(cacheName);

						NodeList sublist = node.getChildNodes();
						for (int t = 0; t < sublist.getLength(); t++) {
							Node subnode = sublist.item(t);
							if (subnode.getNodeType() == Node.ELEMENT_NODE) {
								String name = readAttributeValue(subnode,
										"name");
								indexModel.addProperty(name);
							}
						}
						indexes.put(entityName, indexModel);
					}
				}
				return indexes;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != stream)
						stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		/**
		 * 解析索引配置文件
		 */
		protected static Map<String, IndexModel> readIndex(InputStream stream,
				Class<?> clz) {
			Map<String, IndexModel> indexes = new HashMap<String, IndexModel>();
			logger.info("加载缓存信息" + "配置信息加载组件, 读取配置文件中的缓存索引配置数据.");
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = factory.newDocumentBuilder();
				Document doc = docBuilder.parse(stream);

				Node root = doc.getFirstChild();

				NodeList list = root.getChildNodes();
				for (int j = 0; j < list.getLength(); j++) {
					Node node = list.item(j);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						IndexModel indexModel = new IndexModel();
						String entityName = readAttributeValue(node,
								"entityName");
						String cacheName = readAttributeValue(node, "cacheName");

						indexModel.setEntityName(entityName);
						indexModel.setCacheName(cacheName);

						NodeList sublist = node.getChildNodes();
						for (int t = 0; t < sublist.getLength(); t++) {
							Node subnode = sublist.item(t);
							if (subnode.getNodeType() == Node.ELEMENT_NODE) {
								String name = readAttributeValue(subnode,
										"name");
								indexModel.addProperty(name);
							}
						}
						indexes.put(entityName, indexModel);
					}
				}
				return indexes;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != stream)
						stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}
	
	protected  InputStream getClassPath(String path){
		return this.getClass().getResourceAsStream("/"+path);
	}
	

	/**
	 * 从未知对象中获取字段值
	 * 
	 * @param instance
	 * @param fieldName
	 * @return 如果未有该属性，则返回NULL,否则返回Object对象，基本类型被封装成对象返回
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	protected static class ClassUtil {
		protected static Object getFieldValue(Object instance, String fieldName) {
			Class<?> clz = instance.getClass();
			Field field;
			try {

				field = clz.getDeclaredField(fieldName);
				if (null != field) {
					field.setAccessible(true);
					return field.get(instance);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
