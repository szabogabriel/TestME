package me.test.template;

import java.io.File;

import com.jmtemplate.Template;

import me.test.config.Config;

public enum TemplateLoader {
	
	INSTANCE;
	
	public Template loadTemplate(String templateName) {
		return new Template(getTemplateRoot(), templateName, getReload());
	}
	
	private boolean getReload() {
		return Boolean.parseBoolean(Config.TEMPLATE_RELOAD.toString());
	}
	
	private File getTemplateRoot() {
		return new File(Config.DIR_TEMPLATES.toString());
	}
	
}
