/*
 * This file is part of "SAP Commerce Developers Toolset" plugin for IntelliJ IDEA.
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

package com.intellij.idea.plugin.hybris.impex.psi

import com.intellij.idea.plugin.hybris.impex.constants.HeaderMode
import com.intellij.idea.plugin.hybris.impex.file.ImpexFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.childrenOfType
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType

object ImpExElementFactory {

    fun createHeaderMode(project: Project, mode: HeaderMode) = createFile(project, "$mode Product;")
        .childrenOfType<ImpexHeaderLine>()
        .firstOrNull()
        ?.anyHeaderMode
        ?.firstChild

    fun createParametersSeparator(project: Project) = createFile(project, "INSERT Product;")
        .childrenOfType<ImpexHeaderLine>()
        .firstOrNull()
        ?.lastChild

    fun createMacroName(project: Project, value: String) = createFile(project, "$value = dummy")
        .getChildOfType<ImpexMacroDeclaration>()
        ?.getChildOfType<ImpexMacroNameDec>()

    fun createSingleQuotedString(project: Project, value: String) = createFile(project, "\$macro = '$value'")
        .getChildOfType<ImpexMacroDeclaration>()
        ?.getChildOfType<ImpexString>()

    fun createValueGroup(project: Project, value: String? = "") = createFile(project, """
     INSERT Product;
                   ;$value
    """.trimIndent()
    )
        .childrenOfType<ImpexValueLine>()
        .firstOrNull()
        ?.valueGroupList
        ?.firstOrNull()

    fun createFile(project: Project, text: String): ImpexFile = PsiFileFactory.getInstance(project)
        .createFileFromText("dummy.impex", ImpexFileType, text) as ImpexFile
}