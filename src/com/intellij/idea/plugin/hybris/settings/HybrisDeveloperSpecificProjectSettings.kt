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

package com.intellij.idea.plugin.hybris.settings

import com.intellij.idea.plugin.hybris.flexibleSearch.settings.FlexibleSearchSettings
import com.intellij.idea.plugin.hybris.groovy.settings.GroovySettings
import com.intellij.idea.plugin.hybris.impex.settings.ImpexSettings
import com.intellij.idea.plugin.hybris.polyglotQuery.settings.PolyglotQuerySettings
import com.intellij.idea.plugin.hybris.system.bean.settings.BeanSystemSettings
import com.intellij.idea.plugin.hybris.system.businessProcess.settings.BpSettings
import com.intellij.idea.plugin.hybris.system.cockpitng.settings.CngSettings
import com.intellij.idea.plugin.hybris.system.type.settings.TSDiagramSettings
import com.intellij.idea.plugin.hybris.system.type.settings.TypeSystemSettings
import com.intellij.openapi.components.BaseState

class HybrisDeveloperSpecificProjectSettings : BaseState() {
    var activeRemoteConnectionID by string(null)
    var activeSolrConnectionID by string(null)
    var remoteConnectionSettingsList by list<HybrisRemoteConnectionSettings>()
    var typeSystemDiagramSettings by property(TSDiagramSettings()) { false }
    var beanSystemSettings by property(BeanSystemSettings()) { false }
    var typeSystemSettings by property(TypeSystemSettings()) { false }
    var cngSettings by property(CngSettings()) { false }
    var bpSettings by property(BpSettings()) { false }
    var flexibleSearchSettings by property(FlexibleSearchSettings()) { false }
    var polyglotQuerySettings by property(PolyglotQuerySettings()) { false }
    var impexSettings by property(ImpexSettings()) { false }
    var groovySettings by property(GroovySettings()) { false }
}