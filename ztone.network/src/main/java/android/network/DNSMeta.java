package android.network;

import android.assist.Assert;

class DNSMeta {
	public static final long DTTL = 600000l;

	public final String host;
	public String ip;
	public long ttl;
	public long queryTime;

	public DNSMeta(String host) {
		this.host = host;

		ttl = DTTL;
	}

	/**
	 * 是否过期
	 * 
	 * @return
	 */
	public boolean isExpired() {

		return queryTime + ttl < System.currentTimeMillis();
	}

	@Override
	public String toString() {

		return new StringBuilder().append("host: ").append(host).append(", ip: ").append(ip).toString();
	}

	@Override
	public int hashCode() {

		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DNSMeta)) {
			return false;
		}

		DNSMeta dns = (DNSMeta) obj;

		return (Assert.notEmpty(host) && host.equals(dns.host)) && //
				(Assert.notEmpty(ip) && ip.equals(dns.ip)) && //
				(this.ttl == dns.ttl) && //
				(this.queryTime == dns.queryTime);
	}
}
