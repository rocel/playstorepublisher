package com.rocel.playstorepublisher;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.Apk;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.Track;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class PlayStorePublisherBuilder extends Builder {
    public static final String PLUGIN_NAME = "Play Store Publisher";

    private final String keyFilePath;
    private final String apkPath;
    private final String stage;
    private final String appName;
    private final String packageName;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public PlayStorePublisherBuilder(String keyFilePath, String apkPath, String stage, String appName, String packageName) {
        this.keyFilePath = keyFilePath;
        this.apkPath = apkPath;
        this.stage = stage;
        this.appName = appName;
        this.packageName = packageName;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
        listener.getLogger().println("keyFilePath : " + keyFilePath + " - apkPath :" + apkPath + " - stage :" + stage + " - appName :" + appName + " - packageName :" + packageName);

//        try {
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(packageName), "ApplicationConfig.PACKAGE_NAME cannot be null or empty!");
//
//            // Create the API service.
//            AndroidPublisher service = AndroidPublisherHelper.init(appName, ApplicationConfig.SERVICE_ACCOUNT_EMAIL);
//            final AndroidPublisher.Edits edits = service.edits();
//
//            // Create a new edit to make changes to your listing.
//            AndroidPublisher.Edits.Insert editRequest = edits.insert(packageName, null /** no content */);
//            AppEdit edit = editRequest.execute();
//            final String editId = edit.getId();
//            listener.getLogger().println(String.format("Created edit with id: %s", editId));
//
//            // Upload new apk to developer console
//            final String apkPath = BasicUploadApk.class
//                    .getResource(ApplicationConfig.APK_FILE_PATH)
//                    .toURI().getPath();
//
//            final AbstractInputStreamContent apkFile = new FileContent(AndroidPublisherHelper.MIME_TYPE_APK, new File(apkPath));
//            AndroidPublisher.Edits.Apks.Upload uploadRequest = edits
//                    .apks()
//                    .upload(packageName,
//                            editId,
//                            apkFile);
//
//            Apk apk = uploadRequest.execute();
//            listener.getLogger().println(String.format("Version code %d has been uploaded",
//                    apk.getVersionCode()));
//
//            // Assign apk to alpha track.
//            List<Integer> apkVersionCodes = new ArrayList<Integer>();
//            apkVersionCodes.add(apk.getVersionCode());
//            AndroidPublisher.Edits.Tracks.Update updateTrackRequest = edits
//                    .tracks()
//                    .update(packageName,
//                            editId,
//                            stage,
//                            new Track().setVersionCodes(apkVersionCodes));
//            Track updatedTrack = updateTrackRequest.execute();
//            listener.getLogger().println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
//
//            // Commit changes for edit.
//            AndroidPublisher.Edits.Commit commitRequest = edits.commit(packageName, editId);
//            AppEdit appEdit = commitRequest.execute();
//            listener.getLogger().println(String.format("App edit with id %s has been comitted", appEdit.getId()));
//
//        } catch (IOException | URISyntaxException | GeneralSecurityException ex) {
//            listener.getLogger().println("Excpetion was thrown while uploading apk to alpha track : " + ex.getLocalizedMessage());
//        }
        return true;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * Descriptor for {@link com.rocel.playstorepublisher.PlayStorePublisherBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     * <p/>
     * <p/>
     * See <tt>src/main/resources/hudson/plugins/hello_world/PlayStorePublisherBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        /**
         * In order to load the persisted global configuration, you have to
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value This parameter receives the value that the user has typed.
         * @return Indicates the outcome of the validation. This is sent to the browser.
         * <p/>
         * Note that returning {@link hudson.util.FormValidation#error(String)} does not
         * prevent the form from being saved. It just means that a message
         * will be displayed to the user.
         */
        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Please set a name");
            }
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return PLUGIN_NAME;
        }

    }
}

