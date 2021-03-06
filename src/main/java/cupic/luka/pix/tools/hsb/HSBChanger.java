package cupic.luka.pix.tools.hsb;

import cupic.luka.pix.tools.hsb.filters.AbstractFilter;
import cupic.luka.pix.tools.hsb.filters.BrightnessFilter;
import cupic.luka.pix.tools.hsb.filters.HueFilter;
import cupic.luka.pix.tools.hsb.filters.SaturationFilter;
import cupic.luka.pix.image.ImagePanel;
import cupic.luka.pix.tools.AbstractTool;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * A panel for changing the Hue, Saturation and Brightness
 * components of an image.
 *
 * @author Luka Cupic
 */
public class HSBChanger extends AbstractTool {

	/**
	 * The largest value for the HSB sliders.
	 */
	private static final int MAX_SLIDER_VALUE = 50;

	/**
	 * Hue filter.
	 */
	private final HueFilter hueFilter = new HueFilter();

	/**
	 * Saturation filter.
	 */
	private final SaturationFilter saturationFilter = new SaturationFilter();

	/**
	 * Brightness filter.
	 */
	private final BrightnessFilter brightnessFilter = new BrightnessFilter();

	/**
	 * Creates a new HSBChanger object.
	 *
	 * @param imagePanel the panel holding the image
	 */
	public HSBChanger(ImagePanel imagePanel) {
		super(imagePanel);
		initGUI();
	}

	/**
	 * Initializes the panel GUI.
	 */
	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JSlider hueSlider = createSlider(hueFilter);
		add(new JLabel("Hue:"));
		add(hueSlider);

		JSlider saturationSlider = createSlider(saturationFilter);
		add(new JLabel("Saturation:"));
		add(saturationSlider);

		JSlider brightnessSlider = createSlider(brightnessFilter);
		add(new JLabel("Brightness:"));
		add(brightnessSlider);
	}

	/**
	 * Creates the slider for controlling each of the HSB values.
	 *
	 * @param filter the filter which is to be applied upon changing
	 *               the slider value
	 * @return the slider representing either the Hue, Saturation or
	 * Brightness control slider, depending on the type of the provided
	 * filter
	 */
	private JSlider createSlider(AbstractFilter filter) {
		JSlider slider = new JSlider(0, MAX_SLIDER_VALUE, MAX_SLIDER_VALUE / 2);
		slider.addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			float offset = 1.0f / source.getMaximum() * source.getValue();
			filter.setOffset(2 * offset - 1);
			filter.apply(imagePanel.getImage(), imagePanel.getOriginal());
			imagePanel.repaint();
		});
		return slider;
	}
}
