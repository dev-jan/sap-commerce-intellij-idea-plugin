/*
 * This file is part of "hybris integration" plugin for Intellij IDEA.
 * Copyright (C) 2014-2016 Alexander Bartash <AlexanderBartash@gmail.com>
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

package com.intellij.idea.plugin.hybris.project.configurators.impl;

import com.intellij.idea.plugin.hybris.common.HybrisConstants;
import com.intellij.idea.plugin.hybris.impex.constants.modifier.BooleanModifierValue;
import com.intellij.idea.plugin.hybris.project.configurators.ModuleSettingsConfigurator;
import com.intellij.idea.plugin.hybris.project.descriptors.HybrisModuleDescriptor;
import com.intellij.idea.plugin.hybris.project.descriptors.HybrisModuleDescriptor.DescriptorType;
import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Martin Zdarsky-Jones on 29/09/2016.
 */
public class DefaultModuleSettingsConfigurator implements ModuleSettingsConfigurator {

    @Override
    public void configure(@NotNull final HybrisModuleDescriptor moduleDescriptor, @NotNull final Module javaModule) {
        final DescriptorType descriptorType = moduleDescriptor.getDescriptorType();
        javaModule.setOption(HybrisConstants.DESCRIPTOR_TYPE, descriptorType.name());

        final boolean hasReadOnlySettings = moduleDescriptor.getRootProjectDescriptor().isImportOotbModulesInReadOnlyMode();
        final boolean isReadOnlyType = descriptorType == DescriptorType.OOTB ||
                                       descriptorType == DescriptorType.PLATFORM ||
                                       descriptorType == DescriptorType.EXT;
        String readOnly = Boolean.valueOf(hasReadOnlySettings && isReadOnlyType).toString();

        javaModule.setOption(HybrisConstants.READ_ONLY, readOnly);
    }
}
