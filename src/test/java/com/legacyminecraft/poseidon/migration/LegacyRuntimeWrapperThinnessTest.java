package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyRuntimeWrapperThinnessTest {
    private static final Path PROPERTY_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/PropertyManager.java");
    private static final Path SERVER_GUI_PATH = Paths.get("src/main/java/net/minecraft/server/ServerGUI.java");
    private static final Path SERVER_GUI_COMMAND_LISTENER_PATH = Paths.get("src/main/java/net/minecraft/server/ServerGuiCommandListener.java");
    private static final Path SERVER_WINDOW_ADAPTER_PATH = Paths.get("src/main/java/net/minecraft/server/ServerWindowAdapter.java");
    private static final Path CONSOLE_LOG_FORMATTER_PATH = Paths.get("src/main/java/net/minecraft/server/ConsoleLogFormatter.java");
    private static final Path GUI_LOG_FORMATTER_PATH = Paths.get("src/main/java/net/minecraft/server/GuiLogFormatter.java");
    private static final Path GUI_LOG_OUTPUT_HANDLER_PATH = Paths.get("src/main/java/net/minecraft/server/GuiLogOutputHandler.java");
    private static final Path GUI_STATS_COMPONENT_PATH = Paths.get("src/main/java/net/minecraft/server/GuiStatsComponent.java");
    private static final Path PLAYER_LIST_BOX_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerListBox.java");
    private static final Path CONVERT_PROGRESS_UPDATER_PATH = Paths.get("src/main/java/net/minecraft/server/ConvertProgressUpdater.java");
    private static final Path FONT_ALLOWED_CHARACTERS_PATH = Paths.get("src/main/java/net/minecraft/server/FontAllowedCharacters.java");

    @Test
    public void propertyManagerDelegatesPropertyLifecycleToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PROPERTY_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PropertyFileBehaviour"));
        Assert.assertTrue(text.contains("PROPERTY_FILE_BEHAVIOUR.initialize"));
        Assert.assertTrue(text.contains("PROPERTY_FILE_BEHAVIOUR.save"));
        Assert.assertTrue(text.contains("PROPERTY_FILE_BEHAVIOUR.getString"));
        Assert.assertTrue(text.contains("PROPERTY_FILE_BEHAVIOUR.getInt"));
        Assert.assertTrue(text.contains("PROPERTY_FILE_BEHAVIOUR.getBoolean"));
        Assert.assertFalse(text.contains("this.properties.load(new FileInputStream"));
        Assert.assertFalse(text.contains("this.properties.store(new FileOutputStream"));
        Assert.assertFalse(text.contains("this.properties.containsKey(s)"));
    }

    @Test
    public void serverGuiDelegatesUiCompositionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(SERVER_GUI_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ServerGuiBehaviour"));
        Assert.assertTrue(text.contains("SERVER_GUI_BEHAVIOUR.openWindow"));
        Assert.assertTrue(text.contains("SERVER_GUI_BEHAVIOUR.initializeLayout"));
        Assert.assertTrue(text.contains("SERVER_GUI_BEHAVIOUR.createLogAndChatPanel"));
        Assert.assertFalse(text.contains("UIManager.setLookAndFeel"));
        Assert.assertFalse(text.contains("new JFrame(\"Minecraft server\")"));
        Assert.assertFalse(text.contains("new GuiLogOutputHandler("));
    }

    @Test
    public void serverGuiEventAdaptersDelegateRuntimeActionsToCanonicalBehaviour() throws IOException {
        String commandListener = new String(Files.readAllBytes(SERVER_GUI_COMMAND_LISTENER_PATH), StandardCharsets.UTF_8);
        String windowAdapter = new String(Files.readAllBytes(SERVER_WINDOW_ADAPTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(commandListener.contains("ServerGuiEventBehaviour"));
        Assert.assertTrue(commandListener.contains("SERVER_GUI_EVENT_BEHAVIOUR.onCommandSubmitted"));
        Assert.assertFalse(commandListener.contains("String s = this.a.getText().trim()"));
        Assert.assertFalse(commandListener.contains("issueCommand(s, this.b)"));

        Assert.assertTrue(windowAdapter.contains("ServerGuiEventBehaviour"));
        Assert.assertTrue(windowAdapter.contains("SERVER_GUI_EVENT_BEHAVIOUR.onWindowClosing"));
        Assert.assertFalse(windowAdapter.contains("while (!this.a.isStopped)"));
        Assert.assertFalse(windowAdapter.contains("System.exit(0)"));
    }

    @Test
    public void logFormattersAndGuiLogOutputHandlerDelegateToCanonicalBehaviour() throws IOException {
        String consoleFormatter = new String(Files.readAllBytes(CONSOLE_LOG_FORMATTER_PATH), StandardCharsets.UTF_8);
        String guiFormatter = new String(Files.readAllBytes(GUI_LOG_FORMATTER_PATH), StandardCharsets.UTF_8);
        String guiOutputHandler = new String(Files.readAllBytes(GUI_LOG_OUTPUT_HANDLER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(consoleFormatter.contains("LogFormattingBehaviour"));
        Assert.assertTrue(consoleFormatter.contains("LOG_FORMATTING_BEHAVIOUR.formatConsole"));
        Assert.assertFalse(consoleFormatter.contains("append(\" [INFO] \")"));
        Assert.assertFalse(consoleFormatter.contains("stringwriter"));

        Assert.assertTrue(guiFormatter.contains("LogFormattingBehaviour"));
        Assert.assertTrue(guiFormatter.contains("LOG_FORMATTING_BEHAVIOUR.formatGui"));
        Assert.assertFalse(guiFormatter.contains("append(\"[INFO] \")"));

        Assert.assertTrue(guiOutputHandler.contains("GuiLogOutputBehaviour"));
        Assert.assertTrue(guiOutputHandler.contains("GUI_LOG_OUTPUT_BEHAVIOUR.publish"));
        Assert.assertFalse(guiOutputHandler.contains("this.d.append(this.a.format(logrecord))"));
        Assert.assertFalse(guiOutputHandler.contains("this.c = (this.c + 1) % 1024"));
    }

    @Test
    public void guiStatsAndPlayerListWrappersDelegateToCanonicalBehaviour() throws IOException {
        String statsComponent = new String(Files.readAllBytes(GUI_STATS_COMPONENT_PATH), StandardCharsets.UTF_8);
        String playerListBox = new String(Files.readAllBytes(PLAYER_LIST_BOX_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(statsComponent.contains("GuiStatsComponentBehaviour"));
        Assert.assertTrue(statsComponent.contains("GUI_STATS_COMPONENT_BEHAVIOUR.initialize"));
        Assert.assertTrue(statsComponent.contains("GUI_STATS_COMPONENT_BEHAVIOUR.refresh"));
        Assert.assertTrue(statsComponent.contains("GUI_STATS_COMPONENT_BEHAVIOUR.paint"));
        Assert.assertFalse(statsComponent.contains("Runtime.getRuntime().totalMemory()"));
        Assert.assertFalse(statsComponent.contains("graphics.fillRect(0, 0, 256, 192)"));

        Assert.assertTrue(playerListBox.contains("PlayerListBoxBehaviour"));
        Assert.assertTrue(playerListBox.contains("PLAYER_LIST_BOX_BEHAVIOUR.updateIfDue"));
        Assert.assertFalse(playerListBox.contains("Vector vector = new Vector()"));
        Assert.assertFalse(playerListBox.contains("this.setListData(vector)"));
    }

    @Test
    public void convertProgressUpdaterDelegatesLoggingThrottleToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CONVERT_PROGRESS_UPDATER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConversionProgressBehaviour"));
        Assert.assertTrue(text.contains("CONVERSION_PROGRESS_BEHAVIOUR.initializeTimestamp"));
        Assert.assertTrue(text.contains("CONVERSION_PROGRESS_BEHAVIOUR.maybeLogProgress"));
        Assert.assertFalse(text.contains("if (System.currentTimeMillis() - this.b >= 1000L)"));
        Assert.assertFalse(text.contains("MinecraftServer.log.info(\"Converting... \" + i + \"%\")"));
    }

    @Test
    public void fontAllowedCharactersDelegatesResourceLoadingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(FONT_ALLOWED_CHARACTERS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("AllowedCharacterSetBehaviour"));
        Assert.assertTrue(text.contains("ALLOWED_CHARACTER_SET_BEHAVIOUR.loadAllowedCharacters"));
        Assert.assertFalse(text.contains("new BufferedReader(new InputStreamReader"));
        Assert.assertFalse(text.contains("FontAllowedCharacters.class.getResourceAsStream(\"/font.txt\")"));
    }
}
