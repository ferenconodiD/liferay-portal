/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import CodeMirrorEditor from './CodeMirrorEditor';

const FragmentEditor = ({
	props: {
		allowedStatus = {
			approved: false,
			draft: false,
		},
		cacheable,
		initialCSS,
		initialConfiguration,
		initialHTML,
		initialJS,
		propagationEnabled,
		readOnly,
		status,
	},
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
	const [isCacheable, setIsCacheable] = useState(cacheable);
	const [isSaving, setIsSaving] = useState(false);

	const handleSaveButtonClick = () => {
		setIsSaving(true);
	};

	return (
		<div className="fragment-editor-container">
			<div className="nav-bar-container">
				<div className="navbar navbar-default">
					<div className="container">
						<div className="navbar navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
							<ClayTabs modern>
								<ClayTabs.Item
									active={activeTabKeyValue === 0}
									innerProps={{
										'aria-controls': 'code',
										'aria-expanded': 'true',
									}}
									onClick={() => setActiveTabKeyValue(0)}
								>
									{Liferay.Language.get('code')}
								</ClayTabs.Item>

								<ClayTabs.Item
									active={activeTabKeyValue === 1}
									innerProps={{
										'aria-controls': 'configuration',
										'aria-expanded': 'false',
									}}
									onClick={() => setActiveTabKeyValue(1)}
								>
									{Liferay.Language.get('configuration')}
								</ClayTabs.Item>
							</ClayTabs>
						</div>

						<div className="btn-group btn-group-nowrap float-right mt-1">
							{readOnly ? (
								<span className="pr-3 pt-1 text-info">
									<ClayIcon symbol="exclamation-circle" />

									<span>
										{Liferay.Language.get('read-only-view')}
									</span>
								</span>
							) : (
								<>
									{propagationEnabled && (
										<span
											className="lfr-portal-tooltip pr-3 pt-1 text-info"
											data-title={Liferay.Language.get(
												'automatic-propagation-enabled-help'
											)}
										>
											<ClayIcon symbol="exclamation-circle" />

											<span>
												{Liferay.Language.get(
													'automatic-propagation-enabled'
												)}
											</span>
										</span>
									)}

									<div className="btn-group-item custom-checkbox custom-control ml-2 mr-4 mt-1">
										<label
											className="lfr-portal-tooltip"
											data-title={Liferay.Language.get(
												'cacheable-fragment-help'
											)}
										>
											<input
												checked={isCacheable}
												className="custom-control-input toggle-switch-check"
												name="cacheable"
												onChange={event =>
													setIsCacheable(
														event.currentTarget
															.checked
													)
												}
												type="checkbox"
												value="true"
											/>

											<span className="custom-control-label">
												<span className="custom-control-label-text">
													{Liferay.Language.get(
														'cacheable'
													)}
												</span>
											</span>
										</label>
									</div>

									{status === allowedStatus.draft && (
										<div className="btn-group-item">
											<button
												className="btn btn-secondary btn-sm"
												disabled={isSaving}
												onClick={handleSaveButtonClick}
												type="button"
												value={allowedStatus.draft}
											>
												<span className="lfr-btn-label">
													{Liferay.Language.get(
														'save-as-draft'
													)}
												</span>
											</button>
										</div>
									)}

									<div className="btn-group-item">
										<button
											className="btn btn-primary btn-sm"
											disabled={isSaving}
											onClick={handleSaveButtonClick}
											type="button"
											value={allowedStatus.approved}
										>
											<span className="lfr-btn-label">
												{Liferay.Language.get(
													'publish'
												)}
											</span>
										</button>
									</div>
								</>
							)}
						</div>
					</div>
				</div>
			</div>

			<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
				<ClayTabs.TabPane aria-labelledby="code">
					<div className="fragment-editor">
						<div class="source-editor html">
							<CodeMirrorEditor
								content={initialHTML}
								mode="html"
								readOnly={readOnly}
							/>
						</div>

						<div class="source-editor css">
							<CodeMirrorEditor
								content={initialCSS}
								mode="css"
								readOnly={readOnly}
							/>
						</div>

						<div class="source-editor javascript">
							<CodeMirrorEditor
								codeFooterText="}"
								codeHeaderHelpText={Liferay.Util.sub(
									Liferay.Language.get(
										'parameter-x-provides-access-to-the-current-fragment-node-use-it-to-manipulate-fragment-components'
									),
									['fragmentElement']
								)}
								codeHeaderText="function(fragmentElement, configuration) {"
								content={initialJS}
								mode="javascript"
								readOnly={readOnly}
							/>
						</div>
					</div>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane aria-labelledby="configuration">
					<div class="fragment-editor">
						<CodeMirrorEditor
							content={initialConfiguration}
							mode="json"
							readOnly={readOnly}
						/>
					</div>
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</div>
	);
};

export default FragmentEditor;
