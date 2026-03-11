package net.hytaledepot.templates.plugin.config;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

public final class ConfigDemoCommand extends AbstractCommand {
  private final ConfigPluginState state;
  private final ConfigDemoService demoService;
  private final AtomicLong heartbeatTicks;

  public ConfigDemoCommand(ConfigPluginState state, ConfigDemoService demoService, AtomicLong heartbeatTicks) {
    super("hdconfigdemo", "Runs a demo action for the Config template.");
    setAllowsExtraArguments(true);
    this.state = state;
    this.demoService = demoService;
    this.heartbeatTicks = heartbeatTicks;
  }

  @Override
  protected CompletableFuture<Void> execute(CommandContext ctx) {
    state.incrementCommandRequests();
    String sender = String.valueOf(ctx.sender().getDisplayName());
    String action = parseAction(ctx.getInputString());

    String result = demoService.applyAction(state, sender, action, heartbeatTicks.get());
    ctx.sendMessage(Message.raw(result));
    return CompletableFuture.completedFuture(null);
  }

  private static String parseAction(String input) {
    String normalized = String.valueOf(input == null ? "" : input).trim();
    if (normalized.isEmpty()) {
      return "sample";
    }
    String[] parts = normalized.split("\\s+");
    String first = parts[0].toLowerCase();
    if (first.startsWith("/")) {
      first = first.substring(1);
    }
    if (parts.length > 1 && first.startsWith("hd")) {
      return parts[1].toLowerCase();
    }
    return first;
  }
}
