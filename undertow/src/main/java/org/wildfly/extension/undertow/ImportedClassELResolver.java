/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.extension.undertow;

import javax.el.ELClass;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ImportHandler;
import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Objects;

import org.wildfly.extension.undertow.logging.UndertowLogger;

/**
 * An {@link ELResolver} which supports resolution of EL expressions which use imported classes (for static field/method references)
 *
 * @author Jaikiran Pai
 * @see Section 1.5.3 of EL 3.0 spec
 */
public class ImportedClassELResolver extends ELResolver {


    @Override
    public Object getValue(final ELContext context, final Object base, final Object property) {
        Objects.requireNonNull(context, UndertowLogger.ROOT_LOGGER.nullNotAllowed("ELContext"));
        if (base != null) {
            return null;
        }
        if (!(property instanceof String)) {
            return null;
        }
        final String klassName = (String) property;
        final ImportHandler importHandler = context.getImportHandler();
        if (importHandler == null) {
            return null;
        }
        final Class<?> klass = importHandler.resolveClass(klassName);
        if (klass != null) {
            context.setPropertyResolved(true);
            return new ELClass(klass);
        }
        return null;
    }

    @Override
    public Class<?> getType(final ELContext context, final Object base, final Object property) {
        // we don't set any value on invocation of setValue of this resolver, so this getType method should just return
        // null and *not* mark the base, property combination as resolved
        return null;
    }

    @Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object value) {
        Objects.requireNonNull(context, UndertowLogger.ROOT_LOGGER.nullNotAllowed("ELContext"));
        // we don't allow setting any value so this method
    }

    @Override
    public boolean isReadOnly(final ELContext context, final Object base, final Object property) {
        if (context == null) {
            throw new NullPointerException("ELContext cannot be null");
        }
        // we don't allow setting any value via this resolver, so this is always read-only
        return true;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(final ELContext context, final Object base) {
        return null;
    }
}
