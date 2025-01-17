/*
 * This file is part of "SAP Commerce Developers Toolset" plugin for IntelliJ IDEA.
 * Copyright (C) 2014-2016 Alexander Bartash <AlexanderBartash@gmail.com>
 * Copyright (C) 2019-2024 EPAM Systems <hybrisideaplugin@epam.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.intellij.idea.plugin.hybris.impex.constants.modifier

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.idea.plugin.hybris.common.HybrisConstants
import com.intellij.idea.plugin.hybris.impex.codeInsight.lookup.ImpExLookupElementFactory
import com.intellij.idea.plugin.hybris.impex.completion.ImpexImplementationClassCompletionContributor
import com.intellij.idea.plugin.hybris.impex.psi.ImpexAnyAttributeName
import com.intellij.idea.plugin.hybris.impex.psi.ImpexAnyAttributeValue
import com.intellij.openapi.project.Project

/**
 * https://help.sap.com/docs/SAP_COMMERCE/d0224eca81e249cb821f2cdf45a82ace/1c8f5bebdc6e434782ff0cfdb0ca1847.html?locale=en-US
 */
enum class AttributeModifier(
    override val modifierName: String,
    private val modifierValues: Set<String> = emptySet(),
) : ImpexModifier {

    UNIQUE("unique", HybrisConstants.IMPEX_MODIFIER_BOOLEAN_VALUES),
    ALLOW_NULL("allownull", HybrisConstants.IMPEX_MODIFIER_BOOLEAN_VALUES),
    FORCE_WRITE("forceWrite", HybrisConstants.IMPEX_MODIFIER_BOOLEAN_VALUES),
    IGNORE_KEY_CASE("ignoreKeyCase", HybrisConstants.IMPEX_MODIFIER_BOOLEAN_VALUES),
    IGNORE_NULL("ignorenull", HybrisConstants.IMPEX_MODIFIER_BOOLEAN_VALUES),
    VIRTUAL("virtual", HybrisConstants.IMPEX_MODIFIER_BOOLEAN_VALUES),
    MODE("mode") {
        override fun getLookupElements(project: Project) = mapOf(
            "append" to "(+)",
            "remove" to "(-)",
            "merge" to "(+?)"
        )
            .map { ImpExLookupElementFactory.buildModifierValue(it.key, it.value) }
            .toSet()
    },
    ALIAS("alias"),
    COLLECTION_DELIMITER("collection-delimiter"),
    DATE_FORMAT("dateformat"),
    DEFAULT("default"),
    KEY_2_VALUE_DELIMITER("key2value-delimiter"),
    LANG("lang"),
    MAP_DELIMITER("map-delimiter"),
    NUMBER_FORMAT("numberformat"),
    PATH_DELIMITER("path-delimiter"),
    POS("pos"),
    CELL_DECORATOR("cellDecorator") {
        override fun getLookupElements(project: Project) = ImpexImplementationClassCompletionContributor.getInstance(project)
            .getImplementationsForClasses(HybrisConstants.CLASS_FQN_IMPEX_CELL_DECORATOR)
    },
    TRANSLATOR("translator") {
        override fun getLookupElements(project: Project) = ImpexImplementationClassCompletionContributor.getInstance(project)
            .getImplementationsForClasses(*HybrisConstants.CLASS_FQN_IMPEX_TRANSLATORS)
    },
    EXPR("expr"),
    SYSTEM("system"),
    VERSION("version"),
    CLASSIFICATION_CLASS("class");

    override fun getLookupElements(project: Project): Set<LookupElement> = modifierValues
        .map { ImpExLookupElementFactory.buildModifierValue(it) }
        .toSet()

    companion object {
        private val CACHE = entries.associateBy { it.modifierName }

        fun getModifier(modifierName: String) = CACHE[modifierName]
        fun getModifier(modifierValue: ImpexAnyAttributeValue?) = modifierValue
            ?.anyAttributeName
            ?.let { getModifier(it) }

        fun getModifier(modifierName: ImpexAnyAttributeName?) = modifierName
            ?.text
            ?.let { CACHE[it] }
    }
}
