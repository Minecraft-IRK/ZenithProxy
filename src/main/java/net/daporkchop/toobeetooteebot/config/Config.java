/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2018 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.toobeetooteebot.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NonNull;
import net.daporkchop.lib.binary.UTF8;
import net.daporkchop.toobeetooteebot.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author DaPorkchop_
 */
public class Config implements Constants {
    @Getter
    private final File file;

    private final JsonObject object;
    private volatile boolean dirty;

    public Config(@NonNull String path) {
        this(new File(".", path));
    }

    public Config(@NonNull File file) {
        this.file = file;
        try {
            if (file.exists()) {
                if (!file.isFile()) {
                    throw new IllegalArgumentException(String.format("%s is not a file!", file.getAbsolutePath()));
                }
            } else if (!file.createNewFile()) {
                throw new IllegalStateException(String.format("Could not create file: %s", file.getAbsolutePath()));
            }
            try (InputStream is = new FileInputStream(file)) {
                this.object = JSON_PARSER.parse(new InputStreamReader(is)).getAsJsonObject();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        synchronized (this) {
            if (this.dirty) {
                try (OutputStream os = new FileOutputStream(this.file)) {
                    os.write(GSON.toJson(this.object).getBytes(UTF8.utf8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.dirty = false;
            }
        }
    }

    public JsonElement get(@NonNull String key) {
        JsonElement element = this.object;
        for (String s : key.split("\\.")) {
            element = element.getAsJsonObject().get(s);
        }
        return element;
    }

    public JsonElement set(@NonNull String key, @NonNull Object val) {
        try {
            JsonObject parent = null;
            String name = null;
            JsonElement element = this.object;
            for (String s : key.split("\\.")) {
                element = (parent = element.getAsJsonObject()).get(name = s);
            }
            if (val instanceof JsonElement) {
                parent.add(name, (JsonElement) val);
            } else if (val instanceof String) {
                parent.addProperty(name, (String) val);
            } else if (val instanceof Number) {
                parent.addProperty(name, (Number) val);
            } else if (val instanceof Boolean) {
                parent.addProperty(name, (Boolean) val);
            } else if (val instanceof Character) {
                parent.addProperty(name, (Character) val);
            }
            return parent.get(name);
        } finally {
            this.dirty = true;
        }
    }

    public void remove(@NonNull String key) {
        try {
            JsonObject parent = null;
            String name = null;
            JsonElement element = this.object;
            for (String s : key.split("\\.")) {
                element = (parent = element.getAsJsonObject()).get(name = s);
            }
            parent.remove(name);
        } finally {
            this.dirty = true;
        }
    }

    public boolean getBoolean(String key)   {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def)   {
        JsonElement element = this.get(key);
        if (element == null)    {
            return this.set(key, def).getAsBoolean();
        } else {
            return element.getAsBoolean();
        }
    }

    public int getInt(String key)   {
        return this.getInt(key, 0);
    }

    public int getInt(String key, int def)   {
        JsonElement element = this.get(key);
        if (element == null)    {
            return this.set(key, def).getAsInt();
        } else {
            return element.getAsInt();
        }
    }

    public String getString(String key)   {
        return this.getString(key, "");
    }

    public String getString(String key, @NonNull String def)   {
        JsonElement element = this.get(key);
        if (element == null)    {
            return this.set(key, def).getAsString();
        } else {
            return element.getAsString();
        }
    }

    public JsonArray getArray(String key)   {
        return this.getArray(key, new JsonArray());
    }

    public JsonArray getArray(String key, @NonNull JsonArray def)   {
        JsonElement element = this.get(key);
        if (element == null)    {
            return this.set(key, def).getAsJsonArray();
        } else {
            return element.getAsJsonArray();
        }
    }

    public <T> List<T> getList(String key, @NonNull Function<JsonElement, T> mappingFunction)   {
        return StreamSupport.stream(this.getArray(key).spliterator(), false)
                .map(mappingFunction)
                .collect(Collectors.toList());
    }
}
