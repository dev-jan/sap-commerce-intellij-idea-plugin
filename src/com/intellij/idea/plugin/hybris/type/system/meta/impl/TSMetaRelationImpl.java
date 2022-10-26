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

package com.intellij.idea.plugin.hybris.type.system.meta.impl;

import com.intellij.idea.plugin.hybris.type.system.meta.TSMetaCustomProperty;
import com.intellij.idea.plugin.hybris.type.system.meta.TSMetaDeployment;
import com.intellij.idea.plugin.hybris.type.system.meta.TSMetaModifiers;
import com.intellij.idea.plugin.hybris.type.system.meta.TSMetaRelation;
import com.intellij.idea.plugin.hybris.type.system.model.Cardinality;
import com.intellij.idea.plugin.hybris.type.system.model.Relation;
import com.intellij.idea.plugin.hybris.type.system.model.RelationElement;
import com.intellij.idea.plugin.hybris.type.system.model.Type;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TSMetaRelationImpl extends TSMetaEntityImpl<Relation> implements TSMetaRelation {

    private final TSMetaRelationElement mySourceEnd;
    private final TSMetaRelationElement myTargetEnd;
    private final TSMetaDeployment<TSMetaRelation> myDeployment;
    private final boolean myLocalized;
    private final boolean myAutoCreate;
    private final boolean myGenerate;
    private final String myDescription;

    @SuppressWarnings("ThisEscapedInObjectConstruction")
    public TSMetaRelationImpl(final Module module, final Project project, final String name, final @NotNull Relation dom, final boolean custom) {
        super(module, project, name, dom, custom);
        myLocalized = Boolean.TRUE.equals(dom.getLocalized().getValue());
        myAutoCreate = Boolean.TRUE.equals(dom.getAutoCreate().getValue());
        myGenerate = Boolean.TRUE.equals(dom.getGenerate().getValue());
        myDescription = dom.getDescription().getStringValue();
        mySourceEnd = new TSMetaRelationElementImpl(module, project, this, dom.getSourceElement(), RelationEnd.SOURCE, custom);
        myTargetEnd = new TSMetaRelationElementImpl(module, project, this, dom.getTargetElement(), RelationEnd.TARGET, custom);
        myDeployment = new TSMetaDeploymentImpl<>(module, project, this, dom.getDeployment(), custom);
    }

    @Override
    public TSMetaDeployment<TSMetaRelation> getDeployment() {
        return myDeployment;
    }

    @NotNull
    @Override
    public TSMetaRelation.TSMetaRelationElement getSource() {
        return mySourceEnd;
    }

    @NotNull
    @Override
    public TSMetaRelation.TSMetaRelationElement getTarget() {
        return myTargetEnd;
    }

    @Override
    public boolean isLocalized() {
        return myLocalized;
    }

    @Override
    public boolean isAutoCreate() {
        return myAutoCreate;
    }

    @Override
    public boolean isGenerate() {
        return myGenerate;
    }

    @Override
    public String getDescription() {
        return myDescription;
    }

    private static class TSMetaRelationElementImpl extends TSMetaEntityImpl<RelationElement> implements TSMetaRelationElement {

        private final ConcurrentHashMap<String, TSMetaCustomProperty> myCustomProperties = new CaseInsensitive.CaseInsensitiveConcurrentHashMap<>();
        private final TSMetaModifiers<TSMetaRelationElement> myModifiers;
        private final TSMetaRelation myOwner;
        private final String myType;
        private final String myQualifier;
        private final String myDescription;
        private final String myMetaType;
        private final boolean myNavigable;
        private final boolean myOrdered;
        private final Cardinality myCardinality;
        private final Type myCollectionType;
        private final RelationEnd myEnd;

        public TSMetaRelationElementImpl(final Module module, final Project project, final @NotNull TSMetaRelation owner,
                                         final @NotNull RelationElement dom, final RelationEnd end, final boolean custom) {
            super(module, project, dom, custom);
            myOwner = owner;
            myType = StringUtil.notNullize(dom.getType().getStringValue());
            myEnd = end;
            myQualifier = StringUtil.notNullize(dom.getQualifier().getStringValue());
            myNavigable = Optional.ofNullable(dom.getNavigable().getValue()).orElse(true);
            myOrdered = Boolean.TRUE.equals(dom.getOrdered().getValue());
            myDescription = dom.getDescription().getStringValue();
            myCardinality = dom.getCardinality().getValue();
            myMetaType = dom.getMetaType().getStringValue();
            myCollectionType = Optional.ofNullable(dom.getCollectionType().getValue()).orElse(Type.COLLECTION);
            myModifiers = new TSMetaModifiersImpl<>(module, project, dom.getModifiers(), custom);

            dom.getCustomProperties().getProperties().stream()
               .map(customProperty -> new TSMetaCustomPropertyImpl(module, project, customProperty, custom))
               .filter(metaCustomProperty -> StringUtils.isNotBlank(metaCustomProperty.getName()))
               .forEach(metaCustomProperty -> myCustomProperties.put(metaCustomProperty.getName(), metaCustomProperty));
        }

        @NotNull
        @Override
        public String getType() {
            return myType;
        }

        @Override
        public RelationEnd getEnd() {
            return myEnd;
        }

        @NotNull
        @Override
        public String getQualifier() {
            return myQualifier;
        }

        @Override
        public boolean isNavigable() {
            return myNavigable;
        }

        @NotNull
        @Override
        public TSMetaRelation getOwningRelation() {
            return myOwner;
        }

        @Override
        public Collection<TSMetaCustomProperty> getCustomAttributes() {
            return myCustomProperties.values();
        }

        @Override
        public TSMetaModifiers<TSMetaRelationElement> getModifiers() {
            return myModifiers;
        }

        @Override
        public Cardinality getCardinality() {
            return myCardinality;
        }

        @Override
        public String getDescription() {
            return myDescription;
        }

        @Override
        public Type getCollectionType() {
            return myCollectionType;
        }

        @Override
        public String getMetaType() {
            return myMetaType;
        }

        @Override
        public boolean isOrdered() {
            return myOrdered;
        }
    }
}
