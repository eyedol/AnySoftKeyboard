package com.menny.android.anysoftkeyboard.keyboards;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.menny.android.anysoftkeyboard.AnyKeyboardContextProvider;
import com.menny.android.anysoftkeyboard.R;
import com.menny.android.anysoftkeyboard.keyboards.AnyKeyboard.HardKeyboardTranslator;

public class ExternalAnyKeyboard extends AnyKeyboard implements HardKeyboardTranslator {

	private final String mPrefId;
	private final int mNameResId;
	private final int mIconId;
	private final String mDefaultDictionary;
	private final HardKeyboardSequenceHandler mHardKeyboardTranslator;
	private final String mAdditionalIsLetterExceptions;
	
	protected ExternalAnyKeyboard(AnyKeyboardContextProvider context,
			int xmlLayoutResId,
			int xmlLandscapeResId,
			String prefId,
			int nameResId,
			int iconResId,
			String qwertyTranslation,
			String defaultDictionary,
			String additionalIsLetterExceptions) {
		super(context, getKeyboardId(context, xmlLayoutResId, xmlLandscapeResId));
		mPrefId = prefId;
		mNameResId = nameResId;
		mIconId = iconResId;
		mDefaultDictionary = defaultDictionary;
		if (qwertyTranslation != null)
		{
			mHardKeyboardTranslator = new HardKeyboardSequenceHandler();
			mHardKeyboardTranslator.addQwertyTranslation(qwertyTranslation);
		}
		else
		{
			mHardKeyboardTranslator = null;
		}
		
		mAdditionalIsLetterExceptions = additionalIsLetterExceptions;
	}

	@Override
	public String getDefaultDictionaryLanguage() {
		return mDefaultDictionary;
	}
	
	@Override
	public String getKeyboardPrefId() {
		return mPrefId;
	}
	
	@Override
	public int getKeyboardIcon() {
		return mIconId;
	}
	
	@Override
	public int getKeyboardName() {
		return mNameResId;
	}
	
	private static int getKeyboardId(AnyKeyboardContextProvider context, int portraitId, int landscapeId) 
	{
		final boolean inPortraitMode = 
			(context.getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
		
		if (inPortraitMode)
			return portraitId;
		else
			return landscapeId;
	}
	
	//this class implements the HardKeyboardTranslator interface in an empty way, the physical keyboard is Latin...
	public void translatePhysicalCharacter(HardKeyboardAction action) 
	{
		if (mHardKeyboardTranslator != null)
		{
			final char translated = mHardKeyboardTranslator.getSequenceCharacter(action.getKeyCode(), getKeyboardContext());
			if (translated != 0)
				action.setNewKeyCode(translated);
		}
	}
	
	@Override
	public boolean isLetter(char keyValue) {
		if (mAdditionalIsLetterExceptions == null)
			return super.isLetter(keyValue);
		else
			return super.isLetter(keyValue) || 
				(mAdditionalIsLetterExceptions.indexOf(keyValue) >= 0);
	}

	@Override
	protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
			XmlResourceParser parser) {
		Key key = super.createKeyFromXml(res, parent, x, y, parser);
		//adding icons
		switch(key.codes[0])
		{
		case AnyKeyboard.KEYCODE_DELETE:
			key.icon = res.getDrawable(R.drawable.sym_keyboard_delete_small);
			break;
		case AnyKeyboard.KEYCODE_SHIFT:
			key.icon = res.getDrawable(R.drawable.sym_keyboard_shift);
			break;
		case AnyKeyboard.KEYCODE_CTRL:
			key.icon = res.getDrawable(R.drawable.sym_keyboard_ctrl);
			break;
		case 32://SPACE
			key.icon = res.getDrawable(R.drawable.sym_keyboard_space);
			break;
		case 9://TAB
			key.icon = res.getDrawable(R.drawable.tab_key);
			break;
//these two will be set upon calling setTextVariation			
//		case AnyKeyboard.KEYCODE_SMILEY:
//			key.icon = res.getDrawable(R.drawable.sym_keyboard_smiley);
//			key.popupResId = R.xml.popup_smileys;
//			break;
//		case 10://ENTER
//			key.icon = res.getDrawable(R.drawable.sym_keyboard_return);
//			break;
		}
		
		return key;
	}
	protected void setPopupKeyChars(Key aKey)
	{
		if ((aKey.codes != null) && (aKey.codes.length > 0))
        {
			switch((char)aKey.codes[0])
			{
				case 'a':
					aKey.popupCharacters = "\u00e0\u00e1\u00e2\u00e3\u00e4\u00e5\u00e6\u0105";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'c':
					aKey.popupCharacters = "\u00e7\u0107\u0109\u010d";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'd':
					aKey.popupCharacters = "\u0111";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'e':
					aKey.popupCharacters = "\u00e8\u00e9\u00ea\u00eb\u0119\u20ac";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'g':
					aKey.popupCharacters = "\u011d";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'h':
					aKey.popupCharacters = "\u0125";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'i':
					aKey.popupCharacters = "\u00ec\u00ed\u00ee\u00ef\u0142";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'j':
					aKey.popupCharacters = "\u0135";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'l':
					aKey.popupCharacters = "\u0142";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'o':
					aKey.popupCharacters = "\u00f2\u00f3\u00f4\u00f5\u00f6\u00f8\u0151\u0153";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 's':
					aKey.popupCharacters = "\u00a7\u00df\u015b\u015d\u0161";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'u':
					aKey.popupCharacters = "\u00f9\u00fa\u00fb\u00fc\u016d\u0171";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'n':
					aKey.popupCharacters = "\u00f1";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'y':
					aKey.popupCharacters = "\u00fd\u00ff";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				case 'z':
					aKey.popupCharacters = "\u017c\u017e";
					aKey.popupResId = com.menny.android.anysoftkeyboard.R.xml.popup;
					break;
				default:
					super.setPopupKeyChars(aKey);
			}
        }
	}
}
