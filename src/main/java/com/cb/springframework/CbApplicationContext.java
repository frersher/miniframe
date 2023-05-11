package com.cb.springframework;

import com.cb.springframework.annotation.Autowired;
import com.cb.springframework.annotation.Component;
import com.cb.springframework.annotation.ComponentScan;
import com.cb.springframework.annotation.Lazy;
import com.cb.springframework.annotation.Scope;
import com.cb.springframework.annotation.Transactional;
import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author: chenb
 * @date: 2023/05/11
 **/
public class CbApplicationContext {

  private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
  private Map<String, Object> singleBeanMap = new HashMap<>();

  public CbApplicationContext(Class configClass) {
//    扫描
    scan(configClass);

    for(String beanName:beanDefinitionMap.keySet()){
      BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
      Object singleBean = createBean(beanName, beanDefinition);
      singleBeanMap.put(beanName, singleBean);
    }


  }

  private Object createBean(String beanName, BeanDefinition beanDefinition) {
    Class clazz = beanDefinition.getType();
    try {
      Object o = clazz.newInstance();

      for (Field declaredField : clazz.getDeclaredFields()) {
        if (declaredField.isAnnotationPresent(Autowired.class)){
          Object bean = getBean(declaredField.getName());
          declaredField.setAccessible(true);
          declaredField.set(o,bean);
        }

        if (o instanceof BeanNameAware) {
          ((BeanNameAware)o).setBeanName(beanName);
        }

        if (o instanceof ApplicationContextAware) {
          ((ApplicationContextAware)o).setApplicationContext(this);
        }


        if (clazz.isAnnotationPresent(Transactional.class)){
          Enhancer enhancer = new Enhancer();
          enhancer.setSuperclass(clazz);
          Object target = o;
          enhancer.setCallback((MethodInterceptor) (proxy, method, objects, methodProxy) -> {
            System.out.println("开启事务");
            Object result = method.invoke(target, objects);
            System.out.println("提交事务");
            return result;
          });
          o = enhancer.create();
        }
        return o;


      }

    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void scan(Class configClass) {
//       通过配置类找到扫描路径
    if (configClass.isAnnotationPresent(ComponentScan.class)) {
      ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
      String path = componentScan.value();
      ClassLoader classLoader = this.getClass().getClassLoader();
      path = path.replace(".", "/");
      URL resource = classLoader.getResource(path);
      File file = new File(resource.getFile());
      List<File> classFiles = new ArrayList<>();
      if (file.isDirectory()) {
        for (File f : file.listFiles()) {
          if (f.isDirectory()) {
            for (File f1 : f.listFiles()) {
              if (!f1.isDirectory()) {
                classFiles.add(f1);
              }
            }
          } else {
            classFiles.add(f);
          }
        }
      }

      for (File classFile : classFiles) {
        String absolutePath = classFile.getAbsolutePath();
        String className = absolutePath.substring(absolutePath.indexOf("com"),
                absolutePath.indexOf(".class"))
            .replace("/", ".");

        try {
          Class<?> clazz = classLoader.loadClass(className);
          //   判断是否有@Component注解
          if (clazz.isAnnotationPresent(Component.class)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setType(clazz);
            beanDefinition.setLazy(clazz.isAnnotationPresent(Lazy.class));
            if (clazz.isAnnotationPresent(Scope.class)) {
              beanDefinition.setScope(clazz.getAnnotation(Scope.class).value());
            } else {
              beanDefinition.setScope("singleton");
            }

            String beanName = clazz.getAnnotation(Component.class).value();
            if (beanName.isEmpty()) {
              beanName = Introspector.decapitalize(clazz.getSimpleName());
            }

            beanDefinitionMap.put(beanName, beanDefinition);
          }

        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }

      }


    }

//      如果有，就解析类，判断是否有@Scope注解
//      如果有，就获取@Scope注解的值，如果没有，就默认单例

  }

  public Object getBean(String beanName) {
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    if(beanDefinition == null){
      throw new NullPointerException();
    }
    if ("singleton".equals(beanDefinition.getScope())){
      return singleBeanMap.get(beanName);
    }else {
      return createBean(beanName, beanDefinition);
    }

  }


}
