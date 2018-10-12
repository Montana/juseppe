package ru.lanwen.jenkins.juseppe.gen.source;

import org.junit.Test;
import ru.lanwen.jenkins.juseppe.beans.Plugin;

import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.io.Resources.getResource;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static ru.lanwen.jenkins.juseppe.HPICreationTest.PLUGINS_DIR_CLASSPATH;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class PathPluginSourceTest {

    @Test
    public void shouldFindAllPlugins() throws Exception {
        boolean recursiveWatch = false;
        ClassLoader classloader = getClass().getClassLoader();
        File file = new File(classloader.getResource(PLUGINS_DIR_CLASSPATH).getFile());
        Path path = file.toPath();
        List<Plugin> plugins = new PathPluginSource(path,
                                                    recursiveWatch)
                .plugins();

        assertThat("plugins", plugins, hasSize(2));
        assertThat(plugins.stream().map(Plugin::getName).collect(toList()),
                hasItems("clang-scanbuild-plugin", "jucies-sample-pipeline-dsl-extension"));
    }

    @Test
    public void shouldFindAllPluginsRecursively() throws Exception {
        boolean recursiveWatch = true;
        ClassLoader classloader = getClass().getClassLoader();
        File file = new File(classloader.getResource(PLUGINS_DIR_CLASSPATH).getFile());
        Path path = file.toPath();
        List<Plugin> plugins = new PathPluginSource(path,
                                                    recursiveWatch)
                 .plugins();

        assertThat("plugins", plugins, hasSize(4));
        assertThat(plugins.stream().map(Plugin::getName).collect(toList()),
                   hasItems("clang-scanbuild-plugin", "jucies-sample-pipeline-dsl-extension"));
    }
}
