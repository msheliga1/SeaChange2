package com.seachangesimulations.platform.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.seachangesimulations.platform.dao.ActorDao;
import com.seachangesimulations.platform.dao.PluginDao;
import com.seachangesimulations.platform.pluginobjects.PluginObjectAssociation;
import com.seachangesimulations.platform.pluginobjects.PluginObjectDocument;

@Entity
@Component
@Scope("prototype")
public class Plugin extends BaseSCPlatformObject {

	public Plugin() {

	}

	public Plugin getById(Long id) {
		PluginDao dao = (PluginDao) getApplicationContext().getBean("pluginDao", PluginDao.class);
		return dao.get(id);
	}

	public List<Plugin> getAllUncustomized() {
		PluginDao dao = (PluginDao) getApplicationContext().getBean("pluginDao", PluginDao.class);

		return dao.getAllUncustomized();
	}

	private boolean customized;

	private String author;

	private String authorURI;

	private String description;

	private String language;

	private String license;

	private String pluginName;

	private String pluginURI;

	private String pluginReleaseMajorNumber;

	private String pluginReleaseMinorNumber;

	private String pluginReleaseMicroNumber;

	private String pluginReleaseLetters;

	private String shortFormOrgName;

	private String shortFormPluginName;

	private String pluginDirectory;

	private boolean isSystemPlugin;

	public String generatePluginPath() {
		return "/plugins/" + shortFormPluginName + "_" + shortFormOrgName + "/";
	}

	/**
	 * Constructs the directory to hold the plugin files based on the plugins
	 * name, who constructed it (to disambiguate plugins of the same name by
	 * different creators) and release.
	 * 
	 * @return
	 */
	public String generatePluginDirectory() {
		String pd = shortFormPluginName + "_" + shortFormOrgName;

		if (StringUtils.hasText(pluginReleaseMajorNumber)) {
			pd += "_" + pluginReleaseMajorNumber;
		}
		if (StringUtils.hasText(pluginReleaseMinorNumber)) {
			pd += "_" + pluginReleaseMinorNumber;
		}
		if (StringUtils.hasText(pluginReleaseMicroNumber)) {
			pd += "_" + pluginReleaseMicroNumber;
		}
		if (StringUtils.hasText(pluginReleaseLetters)) {
			pd += "_" + pluginReleaseLetters;
		}
		return pd;

	}

	public boolean isCustomized() {
		return customized;
	}

	public void setCustomized(boolean customized) {
		this.customized = customized;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorURI() {
		return authorURI;
	}

	public void setAuthorURI(String authorURI) {
		this.authorURI = authorURI;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getPluginURI() {
		return pluginURI;
	}

	public void setPluginURI(String pluginURI) {
		this.pluginURI = pluginURI;
	}

	public String getPluginReleaseMajorNumber() {
		return pluginReleaseMajorNumber;
	}

	public void setPluginReleaseMajorNumber(String pluginReleaseMajorNumber) {
		this.pluginReleaseMajorNumber = pluginReleaseMajorNumber;
	}

	public String getPluginReleaseMinorNumber() {
		return pluginReleaseMinorNumber;
	}

	public void setPluginReleaseMinorNumber(String pluginReleaseMinorNumber) {
		this.pluginReleaseMinorNumber = pluginReleaseMinorNumber;
	}

	public String getPluginReleaseMicroNumber() {
		return pluginReleaseMicroNumber;
	}

	public void setPluginReleaseMicroNumber(String pluginReleaseMicroNumber) {
		this.pluginReleaseMicroNumber = pluginReleaseMicroNumber;
	}

	public String getPluginReleaseLetters() {
		return pluginReleaseLetters;
	}

	public void setPluginReleaseLetters(String pluginReleaseLetters) {
		this.pluginReleaseLetters = pluginReleaseLetters;
	}

	public String getShortFormOrgName() {
		return shortFormOrgName;
	}

	public void setShortFormOrgName(String shortFormOrgName) {
		this.shortFormOrgName = shortFormOrgName;
	}

	public String getShortFormPluginName() {
		return shortFormPluginName;
	}

	public void setShortFormPluginName(String shortFormPluginName) {
		this.shortFormPluginName = shortFormPluginName;
	}

	public String getPluginDirectory() {
		return pluginDirectory;
	}

	public void setPluginDirectory(String pluginDirectory) {
		this.pluginDirectory = pluginDirectory;
	}

	public boolean isSystemPlugin() {
		return isSystemPlugin;
	}

	public void setSystemPlugin(boolean isSystemPlugin) {
		this.isSystemPlugin = isSystemPlugin;
	}

	public void save() {

		if (!this.isSystemPlugin) {
			pluginDirectory = this.generatePluginDirectory();
		}

		PluginDao dao = (PluginDao) getApplicationContext().getBean("pluginDao", PluginDao.class);
		dao.save(this);
	}

	/**
	 * Returns a copy of the plugin passed in, and all objects associated with
	 * it.
	 * 
	 * @param rawPluginId
	 * @return
	 */
	public static Plugin getPluginClone(Long rpId, Long rpimId, Long originalPluginId) {

		Plugin originalPlugin = new Plugin().getById(originalPluginId);

		Plugin newPlugin = new Plugin();

		BeanUtils.copyProperties(originalPlugin, newPlugin);

		newPlugin.setId(null);
		newPlugin.setVersion(null);
		newPlugin.save();
		System.out.println("id is " + originalPlugin.getId());

		// Need to get the PluginObjectAssociations here, instead of documents
		List<PluginObjectAssociation> poas = new PluginObjectAssociation().getAllForPlugin(originalPlugin.getId(),
				rpId, rpimId);

		for (PluginObjectAssociation poa : poas) {
			System.out.println(poa.getObjectType());

			Long newObjectId = null;
			String newObjectType = null;
			int newObjectIndex = 0;

			if (PluginObjectDocument.class.getCanonicalName().equalsIgnoreCase(poa.getObjectType())) {

				newObjectType = PluginObjectDocument.class.getCanonicalName();

				PluginObjectDocument pod = new PluginObjectDocument().getById(poa.getObjectId());

				System.out.println(pod.getDocumentName());
				System.out.println(pod.getDocumentDescription());
				System.out.println(pod.getDocumentText());

				PluginObjectDocument newPod = new PluginObjectDocument();
				try {
					BeanUtils.copyProperties(pod, newPod);
				} catch (Exception e) {
					e.printStackTrace();
				}
				newPod.setId(null);
				newPod.setVersion(null);
				newPod.save();
				newObjectId = newPod.getId();

			}

			/* Create and save the new association */
			PluginObjectAssociation newPoa = new PluginObjectAssociation();
			newPoa.setPluginId(newPlugin.getId());
			newPoa.setRpId(rpId);
			newPoa.setRpimId(rpimId);
			newPoa.setObjectIndex(poa.getObjectIndex());
			
			newPoa.setObjectId(newObjectId);
			newPoa.setObjectType(newObjectType);
			
			newPoa.save();
		}

		return newPlugin;

	}
}
