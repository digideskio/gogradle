package com.github.blindpirate.gogradle.vcs.bazaar;

import com.github.blindpirate.gogradle.core.dependency.parse.NotationConverter;

import javax.inject.Singleton;
import java.util.Map;
@Singleton
public class BazaarNotationConverter implements NotationConverter {
    @Override
    public Map<String, Object> convert(String notation) {
        throw new UnsupportedOperationException("Bazaar support is under development now!");
    }
}
