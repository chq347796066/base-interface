package com.spring.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * 统计缓存实例信息
 */
public interface IStatistics {

	long cacheEvictedCount();

	long cacheExpiredCount();

	long cacheHitCount();

	double cacheHitRatio();

	long cacheMissCount();

	long cacheMissExpiredCount();

	long cacheMissNotFoundCount();

	long cachePutAddedCount();

	long cachePutCount();

	long cachePutUpdatedCount();

	long cacheRemoveCount();

	String getAssociatedCacheName();

	long getLocalDiskSize();

	long getLocalDiskSizeInBytes();

	long getLocalHeapSize();

	long getLocalHeapSizeInBytes();

	long getLocalOffHeapSize();

	long getLocalOffHeapSizeInBytes();

	long getRemoteSize();

	long getSize();

	long getWriterQueueLength();

	long localDiskHitCount();

	long localDiskMissCount();

	long localDiskPutAddedCount();

	long localDiskPutCount();

	long localDiskPutUpdatedCount();

	long localDiskRemoveCount();

	long localHeapHitCount();

	long localHeapMissCount();

	long localHeapPutAddedCount();

	long localHeapPutCount();

	long localHeapPutUpdatedCount();

	long localHeapRemoveCount();

	long localOffHeapHitCount();

	long localOffHeapMissCount();

	long localOffHeapPutAddedCount();

	long localOffHeapPutCount();

	long localOffHeapPutUpdatedCount();

	long localOffHeapRemoveCount();

	void setStatisticsTimeToDisable(long time, TimeUnit unit);

	long xaCommitCommittedCount();

	long xaCommitCount();

	long xaCommitExceptionCount();

	long xaCommitReadOnlyCount();

	long xaRecoveryCount();

	long xaRecoveryNothingCount();

	long xaRecoveryRecoveredCount();

	long xaRollbackCount();

	long xaRollbackExceptionCount();

	long xaRollbackSuccessCount();
}
