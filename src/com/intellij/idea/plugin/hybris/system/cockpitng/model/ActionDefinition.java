// Generated on Sat Jan 14 21:57:32 CET 2023
// DTD/Schema  :    null

package com.intellij.idea.plugin.hybris.system.cockpitng.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTag;
import org.jetbrains.annotations.NotNull;

/**
 * null:ActionDefinition interface.
 */
public interface ActionDefinition extends DomElement, ComponentDefinition {

    /**
     * Returns the value of the name child.
     *
     * @return the value of the name child.
     */
    @NotNull
    @SubTag("name")
    GenericDomValue<String> getName();


    /**
     * Returns the value of the description child.
     *
     * @return the value of the description child.
     */
    @NotNull
    @SubTag("description")
    GenericDomValue<String> getDescription();


    /**
     * Returns the value of the author child.
     *
     * @return the value of the author child.
     */
    @NotNull
    @SubTag("author")
    GenericDomValue<String> getAuthor();


    /**
     * Returns the value of the version child.
     *
     * @return the value of the version child.
     */
    @NotNull
    @SubTag("version")
    GenericDomValue<String> getVersion();


    /**
     * Returns the value of the keywords child.
     *
     * @return the value of the keywords child.
     */
    @NotNull
    @SubTag("keywords")
    Keywords getKeywords();


    /**
     * Returns the value of the settings child.
     *
     * @return the value of the settings child.
     */
    @NotNull
    @SubTag("settings")
    Settings getSettings();


    /**
     * Returns the value of the iconUri child.
     *
     * @return the value of the iconUri child.
     */
    @NotNull
    @SubTag("iconUri")
    @Required
    GenericDomValue<String> getIconUri();


    /**
     * Returns the value of the iconDisabledUri child.
     *
     * @return the value of the iconDisabledUri child.
     */
    @NotNull
    @SubTag("iconDisabledUri")
    GenericDomValue<String> getIconDisabledUri();


    /**
     * Returns the value of the iconHoverUri child.
     *
     * @return the value of the iconHoverUri child.
     */
    @NotNull
    @SubTag("iconHoverUri")
    GenericDomValue<String> getIconHoverUri();


    /**
     * Returns the value of the inputType child.
     *
     * @return the value of the inputType child.
     */
    @NotNull
    @SubTag("inputType")
    GenericDomValue<String> getInputType();


    /**
     * Returns the value of the outputType child.
     *
     * @return the value of the outputType child.
     */
    @NotNull
    @SubTag("outputType")
    GenericDomValue<String> getOutputType();


    /**
     * Returns the value of the actionClassName child.
     *
     * @return the value of the actionClassName child.
     */
    @NotNull
    @SubTag("actionClassName")
    GenericDomValue<String> getActionClassName();


    /**
     * Returns the value of the customRendererClassName child.
     *
     * @return the value of the customRendererClassName child.
     */
    @NotNull
    @SubTag("customRendererClassName")
    GenericDomValue<String> getCustomRendererClassName();


    /**
     * Returns the value of the sockets child.
     *
     * @return the value of the sockets child.
     */
    @NotNull
    @SubTag("sockets")
    Sockets getSockets();


}
