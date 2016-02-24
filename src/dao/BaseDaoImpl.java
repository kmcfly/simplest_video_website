/**
 * ��򵥵���Ƶ��վ
 * Simplest Video Website
 *
 * ������ Lei Xiaohua
 * 
 * leixiaohua1020@126.com
 * �й�ý��ѧ/���ֵ��Ӽ���
 * Communication University of China / Digital TV Technology
 * http://blog.csdn.net/leixiaohua1020
 *
 * ��������һ����򵥵���Ƶ��վ��Ƶ����֧��
 * 1.ֱ��
 * 2.�㲥
 * This software is the simplest video website.
 * It support: 
 * 1. live broadcast 
 * 2. VOD 
 */
package dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



/**
 * @author ������
 * HibernateTemplate�ṩ�ǳ���ĳ��÷�������ɻ�Ĳ���������ͨ�������ӡ�ɾ���޸ġ���ѯ�Ȳ�����
 * Spring 2.0�����Ӷ�����SQL��ѯ��֧�֣�Ҳ���ӶԷ�ҳ��֧�֡��󲿷�����£��Ϳ���ɴ����DAO�����CRUD������
 */
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{

	@Override
	public void save(Object object) {
		getHibernateTemplate().save(object);
	}

	@Override
	public void delete(Object object) {
		getHibernateTemplate().delete(object);
	}

	@Override
	public void update(Object object) {
		getHibernateTemplate().update(object);
	}

	@Override
	public Object ReadSingle(final String targetName,final String propertyName, final Object value) {
		// TODO Auto-generated method stub
		return (Object) getHibernateTemplate().execute(new HibernateCallback() {
			/*doInHibernate()��session�Ĵ�������٣�һ�ж��ڳ����ڲ���ɡ�*/
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from "+targetName+" as "+targetName+" where "+targetName+"." + propertyName + "=:value";
				Query query = session.createQuery(hql);
				query.setParameter("value", value);
				return query.uniqueResult();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> ReadAll(String targetName) {
		// TODO Auto-generated method stub
		String hql="from "+targetName;
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> ReadByProperty(final String targetName, final String propertyName,
			final Object value) {
		// TODO Auto-generated method stub
		return (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			/*doInHibernate()��session�Ĵ�������٣�һ�ж��ڳ����ڲ���ɡ�*/
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from "+targetName+" as "+targetName+" where "+targetName+"." + propertyName + "=:value";
				Query query = session.createQuery(hql);
				query.setParameter("value", value);
				return query.list();
			}
		});
	}
	

	@Override
	public List<Object> ReadByPropertyList(String targetName,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}
//������Hibernate2.0֮ǰ�汾list.get(0)���ص���Integer����. 
//������Hibernate3.0�Ժ�汾list.get(0)���ص���Long����. 
//���������ﲻ������Long��ǿת��Integer����. 
//Integer���ڲ��ɸ�����ͣ�����Long��Integerû���κμ̳й�ϵ����Ȼ��������ת����
	@Override
	public Integer ReadCount(final String targetName) {
		// TODO Auto-generated method stub
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			/*doInHibernate()��session�Ĵ�������٣�һ�ж��ڳ����ڲ���ɡ�*/
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "select count(*) from "+targetName;
				//System.out.println(hql);
				//ע:java.lang.Number��Integer,Long�ĸ���.
				return ((Number)session.createQuery(hql).iterate().next()).intValue();
			}
		});
	}
	
	@Override
	public Integer ReadCountByProperty(final String targetName,final String propertyName, final Object value) {
		// TODO Auto-generated method stub
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			/*doInHibernate()��session�Ĵ�������٣�һ�ж��ڳ����ڲ���ɡ�*/
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "select count(*) from "+targetName+" as "+targetName+" where "+targetName+"." + propertyName + "=:value";
				
				Query query = session.createQuery(hql);
				query.setParameter("value", value);
				//System.out.println(query);
				
				return ((Number)query.iterate().next()).intValue();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> ReadLimitedByOrder(final String targetName,
			final String propertyName, final int num, final String order) {
		// TODO Auto-generated method stub
		return (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			/*doInHibernate()��session�Ĵ�������٣�һ�ж��ڳ����ڲ���ɡ�*/
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql ="from "+targetName+" as "+targetName+ " order by "+targetName+"." + propertyName+ " " + order;
				Query query = session.createQuery(hql);
				query.setMaxResults(num);
				//�����ص���ݲ���һ����ʱ�򣬲���uniqueresult()������list()
				return query.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> ReadByPropertyAndLimitedByOrder(final String targetName, final String readpropertyName,
			final Object readvalue,final String orderpropertyName, final int num, final String order) {
		// TODO Auto-generated method stub
		return (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			/*doInHibernate()��session�Ĵ�������٣�һ�ж��ڳ����ڲ���ɡ�*/
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from "+targetName+" as "+targetName+" where "+targetName+"." + readpropertyName + "=:value"+
						" order by "+targetName+"." + orderpropertyName+ " " + order;
				Query query = session.createQuery(hql);
				query.setParameter("value", readvalue);
				query.setMaxResults(num);
				return query.list();
			}
		});
	}

}
