package com.mexel.frmk.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;


public  abstract class BasicService implements IService {
	private BeanFactory beanFactory;
	
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	public BeanFactory getBeanFactory(){
		return this.beanFactory;
	}
	
	
	protected void init() throws Exception{
		
	}
	
	public DataBaseService getDataBaseService(){
		return (DataBaseService)beanFactory.getBean(DataBaseService.class);
	}


	public IService getService(String name){
		return (IService)beanFactory.getBean(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		
	}
}
