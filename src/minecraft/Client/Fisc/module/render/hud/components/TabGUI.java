package Client.Fisc.module.render.hud.components;

import java.awt.Color;
import java.util.ArrayList;

import Client.Fisc.Fisc;
import Client.Fisc.event.EventTarget;
import Client.Fisc.module.Module;
import Client.Fisc.setting.Setting;
import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import Client.Fisc.event.events.misc.EventKeyboard;
import Client.Fisc.event.events.render.EventRender2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class TabGUI {

	private FontRenderer fr;

	private ArrayList<Module.Category> categoryValues;
	private int currentCategoryIndex, currentModIndex, currentSettingIndex;
	private boolean editMode;

	private int screen;

	public TabGUI() {
		this.fr = Minecraft.getMinecraft().fontRendererObj;
		this.categoryValues = new ArrayList();
		this.currentCategoryIndex = 0;
		this.currentModIndex = 0;
		this.currentSettingIndex = 0;
		this.editMode = false;
		this.screen = 0;
		for (Module.Category c : Module.Category.values()) {
			this.categoryValues.add(c);
		}
	}

	@EventTarget
	public void onRedner(EventRender2D e) {
		this.renderTopString(5, 5);
		int startX = 5;
		int startY = (5 + 9) + 2;
		Gui.drawRect(startX, startY, startX + this.getWidestCategory() + 5,
				startY + this.categoryValues.size() * (9 + 2), new Color(0, 0, 0, 190).getRGB());
		for (Module.Category c : this.categoryValues) {
			if (this.getCurrentCategorry().equals(c)) {
				Gui.drawRect(startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + 9 + 2,
						new Color(51, 00, 10, 190).getRGB());
			}

			String name = c.name();
			fr.drawStringWithShadow(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase(),
					startX + 2 + (this.getCurrentCategorry().equals(c) ? 2 : 0), startY + 2, -1);
			startY += 9 + 2;
		}

		if (screen == 1 || screen == 2) {
			int startModsX = startX + this.getWidestCategory() + 6;
			int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (9 + 2);
			Gui.drawRect(startModsX, startModsY, startModsX + this.getWidestMod() + 5,
					startModsY + this.getModsForCurrentCategory().size() * (9 + 2), new Color(0, 0, 0, 190).getRGB());
			for (Module m : getModsForCurrentCategory()) {
				if (this.getCurrentModule().equals(m)) {
					Gui.drawRect(startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
							startModsY + 9 + 2, new Color(51, 00, 10, 190).getRGB());
				}
				fr.drawStringWithShadow(m.getName() + (Fisc.INSTANCE.SETTING_MANAGER.getSettingsForModule(m) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(m) ? 2 : 0),
						startModsY + 2, m.isToggled() ? -1 : Color.GRAY.getRGB());
				startModsY += 9 + 2;
			}
		}
		if (screen == 2) {
			int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
			int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

			Gui.drawRect(startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
					startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), new Color(0, 0, 0, 190).getRGB());
			for (Setting s : this.getSettingForCurrentMod()) {

				if (this.getCurrentSetting().equals(s)) {
					Gui.drawRect(startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1,
							startSettingY + 9 + 2, new Color(51, 00, 10, 190).getRGB());
				}
				if (s.isBoolean()) {
					fr.drawStringWithShadow(s.getName() + ": " + s.getBooleanValue(),
							startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
							editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
				} else if (s.isDigit()) {
					fr.drawStringWithShadow(s.getName() + ": " + s.getCurrentValue(),
							startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
							editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
				} else {
					fr.drawStringWithShadow(s.getName() + ": " + s.getCurrentOption(),
							startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
							editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
				}
				startSettingY += 9 + 2;
			}
		}

	}

	private void renderTopString(int x, int y) {
		fr.drawStringWithShadow(ChatFormatting.WHITE + Fisc.INSTANCE.CLIENT_NAME + ChatFormatting.RESET + " v"
				+ Fisc.INSTANCE.CLIENT_VERSION, x, y, new Color(67, 00, 99).getRGB());
	}

	private void up() {
		if (this.currentCategoryIndex > 0 && this.screen == 0) {
			this.currentCategoryIndex--;
		} else if (this.currentCategoryIndex == 0 && this.screen == 0) {
			this.currentCategoryIndex = this.categoryValues.size() - 1;
		}

		else if (this.currentModIndex > 0 && this.screen == 1) {
			this.currentModIndex--;
		} else if (this.currentModIndex == 0 && this.screen == 1) {
			this.currentModIndex = this.getModsForCurrentCategory().size() - 1;
		}

		else if (this.currentSettingIndex > 0 && this.screen == 2 && !this.editMode) {
			this.currentSettingIndex--;
		} else if (this.currentSettingIndex == 0 && this.screen == 2 && !this.editMode) {
			this.currentSettingIndex = this.getSettingForCurrentMod().size() - 1;
		}

		if (editMode) {
			Setting s = this.getCurrentSetting();
			if (s.isBoolean()) {
				s.setBooleanValue(!s.getBooleanValue());
			} else if (s.isDigit()) {
				if (s.isOnlyInt()) {
					s.setCurrentValue(s.getCurrentValue() + 1);
				} else {
					s.setCurrentValue(s.getCurrentValue() + 0.1);
				}

			} else {
				try {
					s.setCurrentOption(s.getOptions().get(s.getCurrentOptionIndex() - 1));
				} catch (Exception e) {
					s.setCurrentOption(s.getOptions().get(s.getOptions().size() - 1));
				}

			}
		}

	}

	private void down() {
		if (this.currentCategoryIndex < this.categoryValues.size() - 1 && this.screen == 0) {
			this.currentCategoryIndex++;
		} else if (this.currentCategoryIndex == this.categoryValues.size() - 1 && this.screen == 0) {
			this.currentCategoryIndex = 0;
		}

		else if (this.currentModIndex < this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
			this.currentModIndex++;
		} else if (this.currentModIndex == this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
			this.currentModIndex = 0;
		}

		else if (this.currentSettingIndex < this.getSettingForCurrentMod().size() - 1 && this.screen == 2
				&& !this.editMode) {
			this.currentSettingIndex++;
		} else if (this.currentSettingIndex == this.getSettingForCurrentMod().size() - 1 && this.screen == 2
				&& !this.editMode) {
			this.currentSettingIndex = 0;
		}

		if (editMode) {
			Setting s = this.getCurrentSetting();
			if (s.isBoolean()) {
				s.setBooleanValue(!s.getBooleanValue());
			} else if (s.isDigit()) {
				if (s.isOnlyInt()) {
					s.setCurrentValue(s.getCurrentValue() - 1);
				} else {
					s.setCurrentValue(s.getCurrentValue() - 0.1);
				}

			} else {
				try {
					s.setCurrentOption(s.getOptions().get(s.getCurrentOptionIndex() + 1));
				} catch (Exception e) {
					s.setCurrentOption(s.getOptions().get(0));
				}

			}
		}
	}

	private void right(int key) {
		if (this.screen == 0) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            this.getCurrentModule().toggle();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == Keyboard.KEY_RETURN) {
            this.getCurrentModule().toggle();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null) {
            this.screen = 2;
        } else if (this.screen == 2) {
            this.editMode = !this.editMode;
        }

	}

	private void left() {
		if (this.screen == 1) {
			this.screen = 0;
			this.currentModIndex = 0;
		} else if (this.screen == 2) {
			this.screen = 1;
			this.currentSettingIndex = 0;
		}

	}

	@EventTarget
	public void onKey(EventKeyboard e) {
		switch (e.getKey()) {
		case Keyboard.KEY_UP:
			this.up();
			break;
		case Keyboard.KEY_DOWN:
			this.down();
			break;
		case Keyboard.KEY_RIGHT:
			this.right(Keyboard.KEY_RIGHT);
			break;
		case Keyboard.KEY_LEFT:
			this.left();
			break;
		case Keyboard.KEY_RETURN:
			this.right(Keyboard.KEY_RETURN);
			break;
		}
	}

	private Setting getCurrentSetting() {
		return getSettingForCurrentMod().get(currentSettingIndex);

	}

	private ArrayList<Setting> getSettingForCurrentMod() {
		return Fisc.INSTANCE.SETTING_MANAGER.getSettingsForModule(getCurrentModule());
	}

	private Module.Category getCurrentCategorry() {
		return this.categoryValues.get(this.currentCategoryIndex);
	}

	private Module getCurrentModule() {
		return getModsForCurrentCategory().get(currentModIndex);
	}

	private ArrayList<Module> getModsForCurrentCategory() {
		ArrayList<Module> mods = new ArrayList();
		Module.Category c = getCurrentCategorry();
		for (Module m : Fisc.INSTANCE.MODULE_MANAGER.getMods()) {
			if (m.getCategory().equals(c)) {
				mods.add(m);
			}
		}
		return mods;
	}

	private int getWidestSetting() {
		int width = 0;
		for (Setting s : getSettingForCurrentMod()) {
			String name;
			if (s.isBoolean()) {
				name = s.getName() + ": " + s.getBooleanValue();

			} else if (s.isDigit()) {
				name = s.getName() + ": " + s.getCurrentValue();
			} else {
				name = s.getName() + ": " + s.getCurrentOption();
			}
			if (fr.getStringWidth(name) > width) {
				width = fr.getStringWidth(name);
			}
		}
		return width;
	}

	private int getWidestMod() {
		int width = 0;
		for (Module m : Fisc.INSTANCE.MODULE_MANAGER.getMods()) {
			int cWidth = fr.getStringWidth(m.getName());
			if (cWidth > width) {
				width = cWidth;
			}
		}
		return width;
	}

	private int getWidestCategory() {
		int width = 0;
		for (Module.Category c : this.categoryValues) {
			String name = c.name();
			int cWidth = fr.getStringWidth(
					name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
			if (cWidth > width) {
				width = cWidth;
			}
		}
		return width;
	}

}
