package io.devfile.workspace;

import io.devfile.workspace.v1alpha1.DevWorkspace;
import io.devfile.workspace.v1alpha1.DevWorkspaceStatus;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

public class DevWorkspaceReconciler implements Reconciler<DevWorkspace> {
  private final KubernetesClient client;

  public DevWorkspaceReconciler(KubernetesClient client) {
    this.client = client;
  }

  @Override
  public UpdateControl<DevWorkspace> reconcile(DevWorkspace resource, Context context) {
    if (resource.getSpec().getTemplate() == null || resource.getSpec().getTemplate().getProjects().isEmpty()) {
      return UpdateControl.noUpdate();
    }

    DevWorkspaceStatus status = new DevWorkspaceStatus();
    status.setMessage(
        "Project source " + resource.getSpec().getTemplate().getProjects().get(0).getGit().getRemotes().get("origin"));
    status.setWorkspaceId("my workspace");
    resource.setStatus(status);
    return UpdateControl.updateStatus(resource);
  }
}
