/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
    10| the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Loads numbered translation packs and resolves UI copy.
 *
 * <p>Lookup order: active pack → English fallback → {@code No translation :: id}.
 * Templates use {@code {0}}, {@code {1}} placeholders (apostrophes are literal).
 */
public final class Language {

    public static final int ENGLISH = 0;
    private static final String ENGLISH_PACK = "translations/english.json";
    private static final String CATALOG = "translations/_implementedLanguages.json";

    private static Language instance;

    private final JsonMapper mapper = JsonMapper.builder().build();
    private final Map<Integer, String> fallback = new HashMap<>();
    private final Map<Integer, String> text = new HashMap<>();
    private final List<Pack> packs = new ArrayList<>();
    private final List<Runnable> localeListeners = new CopyOnWriteArrayList<>();
    private int currentIndex = ENGLISH;
    private String currentName = "English";

    private record Pack(int index, String name, String path) {
    }

    private Language(int lang) {
        fallback.putAll(readPack(ENGLISH_PACK));
        loadCatalog();
        setLanguage(lang);
    }

    public synchronized static Language get() {
        if (instance == null) {
            instance = new Language(ENGLISH);
        }
        return instance;
    }

    /**
     * Test-only: replace the singleton so tests do not share game state.
     */
    static synchronized void resetForTests() {
        instance = null;
    }

    public String get(int id) {
        var value = text.get(id);
        if (value != null) {
            return value;
        }
        var english = fallback.get(id);
        if (english != null) {
            return english;
        }
        return "No translation :: " + id;
    }

    public String get(LangKey key) {
        return get(key.id());
    }

    public String get(int id, Object... args) {
        var pattern = get(id);
        if (args == null || args.length == 0) {
            return pattern;
        }
        return applyArgs(pattern, args);
    }

    public String get(LangKey key, Object... args) {
        return get(key.id(), args);
    }

    public void setLanguage(int index) {
        var pack = packByIndex(index).orElseGet(() -> packByIndex(ENGLISH).orElseThrow());
        currentIndex = pack.index();
        currentName = pack.name();
        text.clear();
        text.putAll(readPack(pack.path()));
        for (var listener : localeListeners) {
            listener.run();
        }
    }

    public int currentIndex() {
        return currentIndex;
    }

    public String currentName() {
        return currentName;
    }

    public Collection<String> getSupportedLanguages() {
        return packs.stream().map(Pack::name).toList();
    }

    public List<Integer> availableIndexes() {
        return packs.stream().map(Pack::index).toList();
    }

    public void addLocaleListener(Runnable listener) {
        localeListeners.add(Objects.requireNonNull(listener));
    }

    public Map<Integer, String> englishPack() {
        return Map.copyOf(fallback);
    }

    private void loadCatalog() {
        packs.clear();
        var names = readCatalog();
        if (names.isEmpty()) {
            names.put(ENGLISH, "English");
        }
        names.putIfAbsent(ENGLISH, "English");
        names.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    var path = "translations/" + entry.getValue().toLowerCase(Locale.ROOT) + ".json";
                    if (entry.getKey() == ENGLISH) {
                        path = ENGLISH_PACK;
                    }
                    if (resourceExists(path)) {
                        packs.add(new Pack(entry.getKey(), entry.getValue(), path));
                    }
                });
        if (packs.isEmpty()) {
            packs.add(new Pack(ENGLISH, "English", ENGLISH_PACK));
        }
    }

    private java.util.Optional<Pack> packByIndex(int index) {
        return packs.stream().filter(pack -> pack.index() == index).findFirst();
    }

    private Map<Integer, String> readCatalog() {
        var stream = resource(CATALOG);
        if (stream == null) {
            return new HashMap<>();
        }
        try (stream) {
            return new HashMap<>(mapper.readValue(stream, new TypeReference<HashMap<Integer, String>>() {
            }));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return new HashMap<>();
        }
    }

    private Map<Integer, String> readPack(String location) {
        var stream = resource(location);
        if (stream == null) {
            return Map.of();
        }
        try (stream) {
            var parsed = mapper.readValue(stream, new TypeReference<HashMap<Integer, String>>() {
            });
            return parsed == null ? Map.of() : parsed;
        } catch (JacksonException ex) {
            ex.printStackTrace(System.err);
            return Map.of();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return Map.of();
        }
    }

    private static boolean resourceExists(String location) {
        var stream = resource(location);
        if (stream == null) {
            return false;
        }
        try {
            stream.close();
        } catch (Exception ignored) {
        }
        return true;
    }

    private static InputStream resource(String location) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
    }

    static String applyArgs(String pattern, Object... args) {
        var result = pattern;
        for (var i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return result;
    }
}
