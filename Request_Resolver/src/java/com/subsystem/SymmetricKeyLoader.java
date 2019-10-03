package com.subsystem;

import java.util.ResourceBundle;

public abstract class SymmetricKeyLoader {
    private static final String defaultKey = "AHYTRvgtreBHIvfgtBHTYUHbvgBGQWcc";
                                            
    protected String symmetricKey;

        public SymmetricKeyLoader() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config.properties");
            symmetricKey = resourceBundle.getString("symmetricKey");
        } catch (Exception e) {
            symmetricKey = defaultKey;
        }
    }

    SymmetricKeyLoader(String symmetricKey) {
        this.symmetricKey = symmetricKey;
    }
}
