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
package org.wildfly.clustering.ee.infinispan;

/**
 * Locates a value from the cache.
 * @author Paul Ferraro
 */
public interface Locator<K, V> {

    /**
     * Locates the value in the cache with the specified identifier.
     * @param id the cache entry identifier
     * @return the value of the cache entry, or null if not found.
     */
    V findValue(K id);

    /**
     * Returns the value for the specified key, if possible.
     * @param key a cache key
     * @return the value of the cache entry, or null if not found or unavailable.
     */
    default V tryValue(K key) {
        return this.findValue(key);
    }
}
