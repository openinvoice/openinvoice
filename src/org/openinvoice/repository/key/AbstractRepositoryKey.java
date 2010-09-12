package org.openinvoice.repository.key;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jhe
 * Date: Aug 12, 2010
 * Time: 5:22:24 PM
 * (C) Copyright 2010 OpenInvoice.org. All rights are reserved.
 */
public abstract class AbstractRepositoryKey implements RepositoryKey {

    private String keyPattern;
    private String key;
    private List<KeyComponent> keyComponents = new ArrayList<KeyComponent>();

    public AbstractRepositoryKey() {
    }

    public AbstractRepositoryKey(String keyPattern) {
        this.keyPattern = keyPattern;
        this.key = keyPattern;
        this.compose();
    }

    public void compose() {
        initKeyComponents();
        for (KeyComponent keyComponent : keyComponents) {
            if (keyComponents.contains(keyComponent)) {
                setKey(keyComponent.replace(key));
            }
        }
    }

    public abstract void initKeyComponents();

    public List<KeyComponent> getKeyComponents() {
        return keyComponents;
    }

    public String getKeyPattern() {
        return keyPattern;
    }

    public void setKeyPattern(String keyPattern) {
        this.keyPattern = keyPattern;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return key;
    }

    public class KeyComponent {

        private String name;
        private String value;

        public KeyComponent(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String replace(String text) {
            return text.replaceAll(name, value);
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            KeyComponent keyComponent = (KeyComponent) o;

            if (!name.equals(keyComponent.name)) return false;

            return true;
        }

        public int hashCode() {
            return name.hashCode();
        }
    }
}
