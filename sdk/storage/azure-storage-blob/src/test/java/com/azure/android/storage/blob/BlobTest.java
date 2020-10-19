package com.azure.android.storage.blob;

import com.azure.android.storage.blob.models.AccessTier;
import com.azure.android.storage.blob.models.BlobDeleteHeaders;
import com.azure.android.storage.blob.models.BlobDeleteResponse;
import com.azure.android.storage.blob.models.BlobDownloadHeaders;
import com.azure.android.storage.blob.models.BlobDownloadResponse;
import com.azure.android.storage.blob.models.BlobGetPropertiesHeaders;
import com.azure.android.storage.blob.models.BlobGetPropertiesResponse;
import com.azure.android.storage.blob.models.BlobRange;
import com.azure.android.storage.blob.models.BlobRequestConditions;
import com.azure.android.storage.blob.models.BlobStorageException;
import com.azure.android.storage.blob.models.BlobType;
import com.azure.android.storage.blob.models.LeaseStateType;
import com.azure.android.storage.blob.models.LeaseStatusType;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

import static com.azure.android.storage.blob.BlobTestUtils.enableFiddler;
import static com.azure.android.storage.blob.BlobTestUtils.garbageEtag;
import static com.azure.android.storage.blob.BlobTestUtils.generateBlockID;
import static com.azure.android.storage.blob.BlobTestUtils.generateResourceName;
import static com.azure.android.storage.blob.BlobTestUtils.getDefaultData;
import static com.azure.android.storage.blob.BlobTestUtils.getDefaultString;
import static com.azure.android.storage.blob.BlobTestUtils.initializeDefaultAsyncBlobClientBuilder;
import static com.azure.android.storage.blob.BlobTestUtils.initializeDefaultSyncBlobClientBuilder;
import static com.azure.android.storage.blob.BlobTestUtils.newDate;
import static com.azure.android.storage.blob.BlobTestUtils.oldDate;
import static com.azure.android.storage.blob.BlobTestUtils.receivedEtag;
import static com.azure.android.storage.blob.BlobTestUtils.setupMatchCondition;
import static com.azure.android.storage.blob.BlobTestUtils.validateBasicHeaders;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@RunWith(DataProviderRunner.class)
@Ignore
public class BlobTest {
    private String containerName;
    private String blobName;
    private static StorageBlobAsyncClient asyncClient;
    private static StorageBlobClient syncClient;

    // TODO: (gapra) Add iftags
    @DataProvider
    public static Object[][] accessConditionsSuccess() {
        return new Object[][] {
            {null,    null,    null,         null},       // 0
            {oldDate, null,    null,         null},       // 1
            {null,    newDate, null,         null},       // 2
            {null,    null,    receivedEtag, null},       // 3
            {null,    null,    null,         garbageEtag} // 4
        };
    }

    // TODO: (gapra) Add iftags
    @DataProvider
    public static Object[][] accessConditionsFail() {
        return new Object[][] {
            {newDate, null,    null,        null},        // 0
            {null,    oldDate, null,        null},        // 1
            {null,    null,    garbageEtag, null},        // 2
            {null,    null,    null,        receivedEtag} // 3
        };
    }

    @DataProvider
    public static Object[][] downloadRange() {
        return new Object[][] {
            {0L, null, getDefaultString()},                       // 0
            {0L, 5L,   getDefaultString().substring(0, 0 + 5)},   // 1
            {3L, 2L,   getDefaultString().substring(3, 3 + 2)},   // 2
            {1L, 3L,   getDefaultString().substring(1, 1 + 3)}    // 3
        };
    }

    @BeforeClass
    public static void setupClass() {
        asyncClient = initializeDefaultAsyncBlobClientBuilder(enableFiddler()).build();
        syncClient = initializeDefaultSyncBlobClientBuilder(enableFiddler()).build();
    }

    @Before
    public void setupTest() {
        // Create container
        containerName = generateResourceName();
        syncClient.createContainer(containerName);

        // Create blob
        blobName = generateResourceName();
        String blockId = generateBlockID();
        syncClient.stageBlock(containerName, blobName, blockId, getDefaultData(), null);
        List<String> blockIds = new ArrayList<>();
        blockIds.add(blockId);
        syncClient.commitBlockList(containerName, blobName, blockIds, false);
    }

    @After
    public void teardownTest() {
        syncClient.deleteContainer(containerName);
    }

    @Test
    public void getPropertiesValues() {
        // When
        BlobGetPropertiesHeaders response = syncClient.getBlobProperties(containerName, blobName);

        // Then
        assertNotNull(response.getETag());
//        assertFalse(response.getETag().contains("\"")); // Quotes should be scrubbed from etag header values
        assertNotNull(response.getLastModified());
        assertNotNull(response.getRequestId());
        assertNotNull(response.getVersion());
        assertNotNull(response.getDateProperty());
        assertTrue(response.getMetadata() == null || response.getMetadata().isEmpty());
        assertEquals(BlobType.BLOCK_BLOB, response.getBlobType());
        assertNull(response.getCopyCompletionTime()); // tested in "copy"
        assertNull(response.getCopyStatusDescription()); // only returned when the service has errors; cannot validate.
        assertNull(response.getCopyId()); // tested in "abort copy"
        assertNull(response.getCopyProgress()); // tested in "copy"
        assertNull(response.getCopySource()); // tested in "copy"
        assertNull(response.getCopyStatus()); // tested in "copy"
        assertTrue(response.isIncrementalCopy() == null || !response.isIncrementalCopy()); // tested in PageBlob."start incremental copy"
        assertNull(response.getDestinationSnapshot()); // tested in PageBlob."start incremental copy"
        assertNull(response.getLeaseDuration()); // tested in "acquire lease"
        assertEquals(LeaseStateType.AVAILABLE, response.getLeaseState());
        assertEquals(LeaseStatusType.UNLOCKED, response.getLeaseStatus());
        assertTrue(response.getContentLength() >= 0);
        assertNotNull(response.getContentType());
        assertNull(response.getContentMD5());
        assertNull(response.getContentEncoding()); // tested in "set HTTP headers"
        assertNull(response.getContentDisposition()); // tested in "set HTTP headers"
        assertNull(response.getContentLanguage()); // tested in "set HTTP headers"
        assertNull(response.getCacheControl()); // tested in "set HTTP headers"
        assertNull(response.getBlobSequenceNumber()); // tested in PageBlob."create sequence number"
        assertEquals("bytes", response.getAcceptRanges());
        assertNull(response.getBlobCommittedBlockCount()); // tested in AppendBlob."append block"
        assertTrue(response.isServerEncrypted());
        assertEquals(AccessTier.HOT.toString(), response.getAccessTier());
        assertTrue(response.isAccessTierInferred());
        assertNull(response.getArchiveStatus());
        assertNotNull(response.getCreationTime());
        // Tag Count not in BlobProperties.
        // Rehydrate priority not in BlobProperties
        // Is Sealed not in BlobProperties
        // Last Access Time is not in BlobProperties
    }

    @Test
    public void getProperties() {
        // When
        BlobGetPropertiesResponse response = syncClient.getBlobPropertiesWithRestResponse(containerName, blobName,
            null, null, null, null, null, null, null);

        // Then
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @UseDataProvider("accessConditionsSuccess")
    public void getPropertiesAC(OffsetDateTime modified, OffsetDateTime unmodified, String ifMatch, String ifNoneMatch) {
        // Setup
        ifMatch = setupMatchCondition(syncClient, containerName, blobName, ifMatch);
        BlobRequestConditions requestConditions = new BlobRequestConditions()
            .setIfModifiedSince(modified)
            .setIfUnmodifiedSince(unmodified)
            .setIfMatch(ifMatch)
            .setIfNoneMatch(ifNoneMatch);

        // When
        BlobGetPropertiesResponse response = syncClient.getBlobPropertiesWithRestResponse(containerName, blobName, null, null, null, requestConditions, null, null, null);

        // Then
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @UseDataProvider("accessConditionsFail")
    public void getPropertiesACFail(OffsetDateTime modified, OffsetDateTime unmodified, String ifMatch, String ifNoneMatch) {
        // Setup
        ifNoneMatch = setupMatchCondition(syncClient, containerName, blobName, ifNoneMatch);
        BlobRequestConditions requestConditions = new BlobRequestConditions()
            .setIfModifiedSince(modified)
            .setIfUnmodifiedSince(unmodified)
            .setIfMatch(ifMatch)
            .setIfNoneMatch(ifNoneMatch);

        // When
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.getBlobPropertiesWithRestResponse(containerName, blobName, null, null, null, requestConditions, null, null, null));

        // Then
        assertTrue(ex.getStatusCode() == 304 || ex.getStatusCode() == 412);
    }

    @Test
    public void getPropertiesError() {
        // Setup
        String blobName = generateResourceName(); // Blob that does not exist.

        // When
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.getBlobProperties(containerName, blobName));

        // Then
        assertEquals(404, ex.getStatusCode());
    }

    @Test
    public void rawDownloadMin() throws IOException {
        // When
        ResponseBody response = syncClient.rawDownload(containerName, blobName);

        // Then
        assertArrayEquals(getDefaultData(), response.bytes());
    }

    @Test
    public void rawDownloadValues() throws IOException {
        // When
        BlobDownloadResponse response = syncClient.rawDownloadWithRestResponse(containerName, blobName, null, null, null, null, null, null, null, null, null, null);

        // Then
        assertArrayEquals(getDefaultData(), response.getValue().bytes());
        assertEquals(200, response.getStatusCode());
        validateBasicHeaders(response.getHeaders());
        BlobDownloadHeaders headers = response.getDeserializedHeaders();
        assertTrue(headers.getMetadata() == null || headers.getMetadata().isEmpty());
        assertEquals(BlobType.BLOCK_BLOB, headers.getBlobType());
        assertNull(headers.getCopyCompletionTime()); // tested in "copy"
        assertNull(headers.getCopyStatusDescription()); // only returned when the service has errors; cannot validate.
        assertNull(headers.getCopyId()); // tested in "abort copy"
        assertNull(headers.getCopyProgress()); // tested in "copy"
        assertNull(headers.getCopySource()); // tested in "copy"
        assertNull(headers.getCopyStatus()); // tested in "copy"
        assertNull(headers.getLeaseDuration()); // tested in "acquire lease"
        assertEquals(LeaseStateType.AVAILABLE, headers.getLeaseState());
        assertEquals(LeaseStatusType.UNLOCKED, headers.getLeaseStatus());
        assertTrue(headers.getContentLength() >= 0);
        assertNotNull(headers.getContentType());
        assertNull(headers.getContentMd5());
        assertNull(headers.getContentEncoding()); // tested in "set HTTP headers"
        assertNull(headers.getContentDisposition()); // tested in "set HTTP headers"
        assertNull(headers.getContentLanguage()); // tested in "set HTTP headers"
        assertNull(headers.getCacheControl()); // tested in "set HTTP headers"
        assertNull(headers.getBlobSequenceNumber()); // tested in PageBlob."create sequence number"
        assertEquals("bytes", headers.getAcceptRanges());
        assertNull(headers.getBlobCommittedBlockCount()); // tested in AppendBlob."append block"
        assertTrue(headers.isServerEncrypted());
    }

    @Test
    public void rawDownloadEmptyBlob() throws IOException {
        // Setup
        String blobName = generateResourceName(); // Blob that does not exist.
        syncClient.commitBlockList(containerName, blobName, new ArrayList<>(), false);

        // When
        BlobDownloadResponse response = syncClient.rawDownloadWithRestResponse(containerName, blobName, null, null, null, null, null, null, null, null, null, null);

        // Then
        assertEquals("", response.getValue().string());
        assertEquals(200, response.getStatusCode());
        BlobDownloadHeaders headers = response.getDeserializedHeaders();
        assertEquals(0, (long) headers.getContentLength());
    }

    @Test
    @UseDataProvider("downloadRange")
    public void rawDownloadRange(Long offset, Long count, String expectedData) throws IOException {
        // Setup
        BlobRange range = count == null ? new BlobRange(offset) : new BlobRange(offset, count);

        // When
        BlobDownloadResponse response = syncClient.rawDownloadWithRestResponse(containerName, blobName, null, null, range, null, null, null, null, null, null, null);

        // Then
        assertEquals(expectedData, response.getValue().string());
        assertTrue(count == null ? response.getStatusCode() == 200 : response.getStatusCode() == 206);
    }

    @Test
    @UseDataProvider("accessConditionsSuccess")
    public void rawDownloadAC(OffsetDateTime modified, OffsetDateTime unmodified, String ifMatch, String ifNoneMatch) {
        // Setup
        ifMatch = setupMatchCondition(syncClient, containerName, blobName, ifMatch);
        BlobRequestConditions requestConditions = new BlobRequestConditions()
            .setIfModifiedSince(modified)
            .setIfUnmodifiedSince(unmodified)
            .setIfMatch(ifMatch)
            .setIfNoneMatch(ifNoneMatch);

        // When
        BlobDownloadResponse response = syncClient.rawDownloadWithRestResponse(containerName, blobName, null, null, null, requestConditions, null, null, null, null, null, null);

        // Then
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @UseDataProvider("accessConditionsFail")
    public void rawDownloadACFail(OffsetDateTime modified, OffsetDateTime unmodified, String ifMatch, String ifNoneMatch) {
        // Setup
        ifNoneMatch = setupMatchCondition(syncClient, containerName, blobName, ifNoneMatch);
        BlobRequestConditions requestConditions = new BlobRequestConditions()
            .setIfModifiedSince(modified)
            .setIfUnmodifiedSince(unmodified)
            .setIfMatch(ifMatch)
            .setIfNoneMatch(ifNoneMatch);

        // When
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.rawDownloadWithRestResponse(containerName, blobName, null, null, null, requestConditions, null, null, null, null, null, null));

        // Then
        assertTrue(ex.getStatusCode() == 304 || ex.getStatusCode() == 412);
    }

    @Test
    public void rawDownloadMd5() throws IOException, NoSuchAlgorithmException {
        // When
        BlobDownloadResponse response = syncClient.rawDownloadWithRestResponse(containerName, blobName, null, null, new BlobRange(0L, 3L), null, true, null, null, null, null, null);

        // Then
        assertEquals(getDefaultString().substring(0, 3), response.getValue().string());
        assertEquals(206, response.getStatusCode());
        BlobDownloadHeaders headers = response.getDeserializedHeaders();
        assertArrayEquals(MessageDigest.getInstance("MD5").digest(getDefaultString().substring(0, 3).getBytes()), headers.getContentMd5());
    }

    // Download snapshot  TODO (gapra) : Test this if we add support for snapshot creation?

    @Test
    public void rawDownloadError() {
        // Setup
        String blobName = generateResourceName(); // Blob that does not exist.

        // When
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.rawDownload(containerName, blobName));

        // Then
        assertEquals(404, ex.getStatusCode());
    }

    @Test
    public void deleteMin() {
        // When
        syncClient.deleteBlob(containerName, blobName);

        // Then
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.getBlobProperties(containerName, blobName));
        assertEquals(404, ex.getStatusCode());
        assertEquals("BlobNotFound", ex.getErrorCode().toString());
    }

    @Test
    public void delete() {
        // When
        BlobDeleteResponse response = syncClient.deleteBlobWithRestResponse(containerName, blobName, null, null, null, null, null, null, null);

        // Then
        assertEquals(202, response.getStatusCode());
        BlobDeleteHeaders headers = response.getDeserializedHeaders();
        assertNotNull(headers.getRequestId());
        assertNotNull(headers.getVersion());
        assertNotNull(headers.getDateProperty());
    }

    // Delete options TODO (gapra) : Test this if we add support for snapshot creation?

    @Test
    @UseDataProvider("accessConditionsSuccess")
    public void deleteAC(OffsetDateTime modified, OffsetDateTime unmodified, String ifMatch, String ifNoneMatch) {
        // Setup
        ifMatch = setupMatchCondition(syncClient, containerName, blobName, ifMatch);
        BlobRequestConditions requestConditions = new BlobRequestConditions()
            .setIfModifiedSince(modified)
            .setIfUnmodifiedSince(unmodified)
            .setIfMatch(ifMatch)
            .setIfNoneMatch(ifNoneMatch);

        // When
        BlobDeleteResponse response = syncClient.deleteBlobWithRestResponse(containerName, blobName, null, null ,null,  null, requestConditions, null, null);

        // Then
        assertEquals(202, response.getStatusCode());
    }

    @Test
    @UseDataProvider("accessConditionsFail")
    public void deleteACFail(OffsetDateTime modified, OffsetDateTime unmodified, String ifMatch, String ifNoneMatch) {
        // Setup
        ifNoneMatch = setupMatchCondition(syncClient, containerName, blobName, ifNoneMatch);
        BlobRequestConditions requestConditions = new BlobRequestConditions()
            .setIfModifiedSince(modified)
            .setIfUnmodifiedSince(unmodified)
            .setIfMatch(ifMatch)
            .setIfNoneMatch(ifNoneMatch);

        // When
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.deleteBlobWithRestResponse(containerName, blobName, null, null ,null,  null, requestConditions, null, null));

        // Then
        assertEquals(412, ex.getStatusCode());
    }

    @Test
    public void deleteError() {
        // Setup
        String blobName = generateResourceName(); // Blob that does not exist.

        // When
        BlobStorageException ex = assertThrows(BlobStorageException.class,
            () -> syncClient.deleteBlob(containerName, blobName));

        // Then
        assertEquals(404, ex.getStatusCode());
    }
}
