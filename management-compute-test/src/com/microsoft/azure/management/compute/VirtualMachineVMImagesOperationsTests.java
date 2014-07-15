/*
 * Copyright Microsoft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.azure.management.compute;

import java.util.ArrayList;

import junit.framework.Assert;

import com.microsoft.azure.management.compute.models.*;

public class VirtualMachineVMImagesOperationsTests extends ComputeManagementIntegrationTestBase {    
    @Override
    public void setUp() throws Exception {
        createComputeManagementClient();
    }

    public void testListVirtualMachineVMImagesSuccess() throws Exception {
        VirtualMachineVMImageListResponse virtualMachineImageListResponse = computeManagementClient.getVirtualMachineVMImagesOperations().list();
        Assert.assertEquals(200, virtualMachineImageListResponse.getStatusCode());
        Assert.assertNotNull(virtualMachineImageListResponse.getRequestId());

        ArrayList<VirtualMachineVMImageListResponse.VirtualMachineVMImage> virtualMachineVMImagelist = virtualMachineImageListResponse.getVMImages();
        for (VirtualMachineVMImageListResponse.VirtualMachineVMImage virtualMachineVMImage : virtualMachineVMImagelist)              
        {
            Assert.assertNotNull(virtualMachineVMImage.getName());
        }
    }
}