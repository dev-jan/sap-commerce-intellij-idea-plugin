/*
 * This file is part of "SAP Commerce Developers Toolset" plugin for Intellij IDEA.
 * Copyright (C) 2019-2023 EPAM Systems <hybrisideaplugin@epam.com> and contributors
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
package com.intellij.idea.plugin.hybris.impex.completion.provider

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.idea.plugin.hybris.impex.psi.ImpexHeaderLine
import com.intellij.idea.plugin.hybris.impex.utils.ProjectPropertiesUtils
import com.intellij.idea.plugin.hybris.system.type.codeInsight.completion.TSCompletionService
import com.intellij.idea.plugin.hybris.system.type.codeInsight.lookup.TSLookupElementFactory
import com.intellij.idea.plugin.hybris.system.type.meta.model.TSMetaType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

class ImpexHeaderItemTypeAttributeNameCompletionProvider : CompletionProvider<CompletionParameters>() {

    public override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val project = parameters.editor.project ?: return
        val element = parameters.position

        val typeCode = PsiTreeUtil.getParentOfType(element, ImpexHeaderLine::class.java)
            ?.getFullHeaderType()
            ?.getHeaderTypeName()
            ?.text
            ?: return

        // See: https://help.sap.com/docs/SAP_COMMERCE/d0224eca81e249cb821f2cdf45a82ace/2fb5a2a780c94325b4a48ff62b36ab23.html#using-header-abbreviations

        ProjectPropertiesUtils.findAutoCompleteProperties(project, "impex.header.replacement")
            .mapNotNull { it.value }
            .mapNotNull { abbreviation ->
                abbreviation
                    .split("...")
                    .takeIf { it.size == 2 }
                    ?.map { it.trim() }
            }
            .mapNotNull { it.firstOrNull() }
            .map { TSLookupElementFactory.buildHeaderAbbreviation(it) }
            .forEach { result.addElement(it) }

        val completions = TSCompletionService.getInstance(project).getCompletions(typeCode, TSMetaType.META_ITEM, TSMetaType.META_ENUM, TSMetaType.META_RELATION)
        result.caseInsensitive().addAllElements(completions)
    }

    companion object {
        val instance: CompletionProvider<CompletionParameters> = ApplicationManager.getApplication().getService(ImpexHeaderItemTypeAttributeNameCompletionProvider::class.java)
    }
}
