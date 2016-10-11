package gov.ca.emsa.pulse.broker.dao;

import gov.ca.emsa.pulse.broker.BrokerApplicationTestConfig;
import gov.ca.emsa.pulse.broker.dto.AddressDTO;
import gov.ca.emsa.pulse.broker.dto.AlternateCareFacilityDTO;
import gov.ca.emsa.pulse.broker.dto.GivenNameDTO;
import gov.ca.emsa.pulse.broker.dto.NameTypeDTO;
import gov.ca.emsa.pulse.broker.dto.OrganizationDTO;
import gov.ca.emsa.pulse.broker.dto.PatientDTO;
import gov.ca.emsa.pulse.broker.dto.PatientOrganizationMapDTO;
import gov.ca.emsa.pulse.broker.dto.PatientRecordDTO;
import gov.ca.emsa.pulse.common.domain.NameType;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={BrokerApplicationTestConfig.class})
public class PatientDaoTest extends TestCase {

	@Autowired QueryDAO queryDao;
	@Autowired AddressDAO addrDao;
	@Autowired OrganizationDAO orgDao;
	@Autowired AlternateCareFacilityDAO acfDao;
	@Autowired PatientDAO patientDao;
	@Autowired PatientRecordDAO prDao;
	private AlternateCareFacilityDTO acf;
	private OrganizationDTO org1, org2;
	private PatientRecordDTO queryResult1, queryResult2;
	
	@Before
	public void setup() {
		acf = new AlternateCareFacilityDTO();
		acf.setName("ACF1");
		acf = acfDao.create(acf);
		assertNotNull(acf);
		assertNotNull(acf.getId());
		assertTrue(acf.getId().longValue() > 0);
		
		org1 = new OrganizationDTO();
		org1.setOrganizationId(1L);
		org1.setName("IHE Org");
		org1.setAdapter("IHE");
		org1.setEndpointUrl("http://www.localhost.com");
		org1.setPassword("pwd");
		org1.setUsername("kekey");
		org1.setActive(true);
		org1 = orgDao.create(org1);
		
		org2 = new OrganizationDTO();
		org2.setOrganizationId(2L);
		org2.setName("eHealth Org");
		org2.setAdapter("eHealth");
		org2.setEndpointUrl("http://www.localhost.com");
		org2.setPassword("pwd");
		org2.setUsername("kekey");
		org2.setActive(true);
		org2 = orgDao.create(org2);
	}
	
	@Test
	@Transactional
	//@Rollback(false)
	public void testCreatePatientMultipleGivens() {		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Jonathon");
		GivenNameDTO given2 = new GivenNameDTO();
		given2.setGivenName("Johnathon");
		GivenNameDTO given3 = new GivenNameDTO();
		given3.setGivenName("Jonny");
		givens.add(given);
		givens.add(given2);
		givens.add(given3);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		toCreate.getPatientName().setSuffix("MD");
		toCreate.getPatientName().setPrefix("Dr.");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		
		String streetLine1 = "1000 Hilltop Circle";
		String city = "Baltimore";
		String state = "MD";
		String zip = "21227";
		AddressDTO addrDto = new AddressDTO();
		addrDto.setStreetLineOne(streetLine1);
		addrDto.setCity(city);
		addrDto.setState(state);
		addrDto.setZipcode(zip);
		addrDto = addrDao.create(addrDto);
		Assert.assertNotNull(addrDto);
		Assert.assertNotNull(addrDto.getId());
		Assert.assertTrue(addrDto.getId().longValue() > 0);
		long existingAddrId = addrDto.getId().longValue();
		toCreate.setAddress(addrDto);
		
		PatientDTO created = patientDao.create(toCreate);
		assertNotNull(created);
		assertNotNull(created.getId());
		assertTrue(created.getId().longValue() > 0);
		assertNotNull(created.getAcf());
		assertNotNull(created.getAcf().getId());
		assertEquals(created.getAcf().getId().longValue(), acf.getId().longValue());
		
		PatientDTO selected = patientDao.getById(created.getId());
		assertNotNull(selected);
		assertNotNull(selected.getId());
		assertTrue(selected.getId().longValue() > 0);
		assertNotNull(selected.getAcf());
		assertNotNull(selected.getAcf().getId());
		assertEquals(selected.getAcf().getId().longValue(), acf.getId().longValue());
		assertEquals(0, selected.getOrgMaps().size());
		assertEquals(toCreate.getPatientName().getFamilyName(), selected.getPatientName().getFamilyName());
		assertEquals(toCreate.getPatientName().getSuffix(), selected.getPatientName().getSuffix());
		assertEquals(toCreate.getPatientName().getNameType().getCode(), selected.getPatientName().getNameType().getCode());
		ArrayList<String> selectedNames = new ArrayList<String>();
		for(GivenNameDTO selectedName : selected.getPatientName().getGivenName()){
			selectedNames.add(selectedName.getGivenName());
		}
		ArrayList<String> insertedNames = new ArrayList<String>();
		for(GivenNameDTO insertedName : toCreate.getPatientName().getGivenName()){
			insertedNames.add(insertedName.getGivenName());
		}
		assertTrue(insertedNames.containsAll(selectedNames));
	}
	
	@Test
	@Transactional
	//@Rollback(true)
	public void testCreatePatientNoAddress() {		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Jonathon");
		givens.add(given);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		toCreate.getPatientName().setSuffix("MD");
		toCreate.getPatientName().setPrefix("Dr.");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		
		PatientDTO created = patientDao.create(toCreate);
		assertNotNull(created);
		assertNotNull(created.getId());
		assertTrue(created.getId().longValue() > 0);
		assertNotNull(created.getAcf());
		assertNotNull(created.getAcf().getId());
		assertEquals(created.getAcf().getId().longValue(), acf.getId().longValue());
		
		PatientDTO selected = patientDao.getById(created.getId());
		assertNotNull(selected);
		assertNotNull(selected.getId());
		assertTrue(selected.getId().longValue() > 0);
		assertNotNull(selected.getAcf());
		assertNotNull(selected.getAcf().getId());
		assertEquals(selected.getAcf().getId().longValue(), acf.getId().longValue());
		assertEquals(0, selected.getOrgMaps().size());
		assertEquals(toCreate.getPatientName().getFamilyName(), selected.getPatientName().getFamilyName());
		assertEquals(toCreate.getPatientName().getSuffix(), selected.getPatientName().getSuffix());
		assertEquals(toCreate.getPatientName().getNameType().getCode(), selected.getPatientName().getNameType().getCode());
	}
	
	@Test
	@Transactional
	//@Rollback(true)
	public void testCreatePatientWithExistingAddress() {		
		String streetLine1 = "1000 Hilltop Circle";
		String city = "Baltimore";
		String state = "MD";
		String zip = "21227";
		AddressDTO addrDto = new AddressDTO();
		addrDto.setStreetLineOne(streetLine1);
		addrDto.setCity(city);
		addrDto.setState(state);
		addrDto.setZipcode(zip);
		addrDto = addrDao.create(addrDto);
		Assert.assertNotNull(addrDto);
		Assert.assertNotNull(addrDto.getId());
		Assert.assertTrue(addrDto.getId().longValue() > 0);
		long existingAddrId = addrDto.getId().longValue();
		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Jonathon");
		givens.add(given);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		toCreate.setAddress(addrDto);
		
		PatientDTO created = patientDao.create(toCreate);
		assertNotNull(created);
		assertNotNull(created.getId());
		assertTrue(created.getId().longValue() > 0);
		assertNotNull(created.getAddress());
		assertNotNull(created.getAddress().getId());
		assertEquals(existingAddrId, created.getAddress().getId().longValue());
		assertNotNull(created.getAcf());
		assertNotNull(created.getAcf().getId());
		assertEquals(created.getAcf().getId().longValue(), acf.getId().longValue());
		
		PatientDTO selected = patientDao.getById(created.getId());
		assertNotNull(selected);
		assertNotNull(selected.getId());
		assertTrue(selected.getId().longValue() > 0);
		assertNotNull(selected.getAddress());
		assertNotNull(selected.getAddress().getId());
		assertEquals(existingAddrId, selected.getAddress().getId().longValue());
		assertNotNull(selected.getAcf());
		assertNotNull(selected.getAcf().getId());
		assertEquals(selected.getAcf().getId().longValue(), acf.getId().longValue());
		assertEquals(0, selected.getOrgMaps().size());
	}
	
	@Test
	@Transactional
	//@Rollback(true)
	public void testCreatePatientWithNewAddress() {		
		String streetLine1 = "1000 Hilltop Circle";
		String city = "Baltimore";
		String state = "MD";
		String zip = "21227";
		AddressDTO addrDto = new AddressDTO();
		addrDto.setStreetLineOne(streetLine1);
		addrDto.setCity(city);
		addrDto.setState(state);
		addrDto.setZipcode(zip);
		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Jonathon");
		givens.add(given);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		toCreate.setAddress(addrDto);
		
		PatientDTO created = patientDao.create(toCreate);
		assertNotNull(created);
		assertNotNull(created.getId());
		assertTrue(created.getId().longValue() > 0);
		assertNotNull(created.getAddress());
		assertNotNull(created.getAddress().getId());
		assertTrue(created.getAddress().getId().longValue() > 0);
		assertNotNull(created.getAcf());
		assertNotNull(created.getAcf().getId());
		assertEquals(created.getAcf().getId().longValue(), acf.getId().longValue());
		
		PatientDTO selected = patientDao.getById(created.getId());
		assertNotNull(selected);
		assertNotNull(selected.getId());
		assertTrue(selected.getId().longValue() > 0);
		assertNotNull(selected.getAddress());
		assertNotNull(selected.getAddress().getId());
		assertTrue(selected.getAddress().getId().longValue() > 0);
		assertNotNull(selected.getAcf());
		assertNotNull(selected.getAcf().getId());
		assertEquals(selected.getAcf().getId().longValue(), acf.getId().longValue());
		assertEquals(0, selected.getOrgMaps().size());
	}
	
	//TODO: the org maps aren't coming back from the create or select 
	//but they do appear when making calls via POSTman.. can't figure out the disconnect
	@Test
	@Transactional
	//@Rollback(true)
	public void testCreatePatientWithOrgMaps() {		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Jonathon");
		givens.add(given);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		
		PatientDTO created = patientDao.create(toCreate);
		assertNotNull(created);
		assertNotNull(created.getId());
		assertTrue(created.getId().longValue() > 0);
		assertNotNull(created.getAcf());
		assertNotNull(created.getAcf().getId());
		assertEquals(created.getAcf().getId().longValue(), acf.getId().longValue());
		
		PatientOrganizationMapDTO orgMap = new PatientOrganizationMapDTO();
		orgMap.setOrg(org1);
		orgMap.setOrganizationId(org1.getId());
		orgMap.setOrgPatientId("JSMITH1");
		orgMap.setPatientId(created.getId());
		orgMap = patientDao.createOrgMap(orgMap);
		
		assertNotNull(orgMap);
		assertNotNull(orgMap.getId());
		assertTrue(orgMap.getId().longValue() > 0);
		
		PatientDTO selected = patientDao.getById(created.getId());
		assertNotNull(selected);
		assertNotNull(selected.getOrgMaps());
		//TODO: this is not working but should be
		//assertTrue(selected.getOrgMaps().size() == 1);
		//assertEquals(orgMap.getId().longValue(), selected.getOrgMaps().get(0).getId().longValue());
	}
	
	@Test
	@Transactional
	//@Rollback(true)
	public void testUpdatePatientFirstName() {		
		String streetLine1 = "1000 Hilltop Circle";
		String city = "Baltimore";
		String state = "MD";
		String zip = "21227";
		AddressDTO addrDto = new AddressDTO();
		addrDto.setStreetLineOne(streetLine1);
		addrDto.setCity(city);
		addrDto.setState(state);
		addrDto.setZipcode(zip);
		addrDto = addrDao.create(addrDto);
		Assert.assertNotNull(addrDto);
		Assert.assertNotNull(addrDto.getId());
		Assert.assertTrue(addrDto.getId().longValue() > 0);
		long existingAddrId = addrDto.getId().longValue();
		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Johnathan");
		givens.add(given);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		toCreate.setAddress(addrDto);
		
		PatientDTO created = patientDao.create(toCreate);
		givens.remove(0);
		GivenNameDTO given2 = new GivenNameDTO();
		given.setGivenName("Johnathan");
		givens.add(given2);
		toCreate.getPatientName().setGivenName(givens);
		PatientDTO updated = patientDao.update(created);
		assertNotNull(updated);
		assertEquals(updated.getId().longValue(), created.getId().longValue());
		assertEquals("Johnathan", updated.getPatientName().getGivenName().get(0).getGivenName());
	}
	
	@Test
	@Transactional
	//@Rollback(true)
	@Commit
	public void testDeletePatient() {		
		String streetLine1 = "1000 Hilltop Circle";
		String city = "Baltimore";
		String state = "MD";
		String zip = "21227";
		AddressDTO addrDto = new AddressDTO();
		addrDto.setStreetLineOne(streetLine1);
		addrDto.setCity(city);
		addrDto.setState(state);
		addrDto.setZipcode(zip);
		addrDto = addrDao.create(addrDto);
		Assert.assertNotNull(addrDto);
		Assert.assertNotNull(addrDto.getId());
		Assert.assertTrue(addrDto.getId().longValue() > 0);
		long existingAddrId = addrDto.getId().longValue();
		
		PatientDTO toCreate = new PatientDTO();
		toCreate.setAcf(acf);
		ArrayList<GivenNameDTO> givens = new ArrayList<GivenNameDTO>();
		GivenNameDTO given = new GivenNameDTO();
		given.setGivenName("Jonathon");
		givens.add(given);
		toCreate.getPatientName().setGivenName(givens);
		toCreate.getPatientName().setFamilyName("Smith");
		NameTypeDTO nameTypeDTO = new NameTypeDTO();
		nameTypeDTO.setCode("L");
		toCreate.getPatientName().setNameType(nameTypeDTO);
		toCreate.setPhoneNumber("4105554444");
		toCreate.setSsn("111223344");
		toCreate.setGender("Male");
		toCreate.setAddress(addrDto);
		
		PatientDTO created = patientDao.create(toCreate);
		patientDao.delete(created.getId());
		
		PatientDTO selected = patientDao.getById(created.getId());
		assertNull(selected);
		AddressDTO selectedAddress = addrDao.getById(existingAddrId);
		assertNull(selectedAddress);
		
		AlternateCareFacilityDTO selectedAcf = acfDao.getById(acf.getId());
		assertNotNull(selectedAcf);
	}
}
